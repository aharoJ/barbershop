package com.barberos.shop.internal;

import com.barberos.identity.GhostService;
import com.barberos.identity.ProfileEntity;
import com.barberos.shop.*;
import com.barberos.shared.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ShopService {

    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    private final ShopRepository shopRepository;
    private final ShopMembershipRepository membershipRepository;
    private final SeatRepository seatRepository;
    private final BarberShopAssociationRepository associationRepository;
    private final ShopOperatingHoursRepository hoursRepository;
    private final ShopBarberInvitationRepository invitationRepository;
    private final ShopSlugGenerator slugGenerator;
    private final GhostService ghostService;

    public ShopService(ShopRepository shopRepository,
                       ShopMembershipRepository membershipRepository,
                       SeatRepository seatRepository,
                       BarberShopAssociationRepository associationRepository,
                       ShopOperatingHoursRepository hoursRepository,
                       ShopBarberInvitationRepository invitationRepository,
                       ShopSlugGenerator slugGenerator,
                       GhostService ghostService) {
        this.shopRepository = shopRepository;
        this.membershipRepository = membershipRepository;
        this.seatRepository = seatRepository;
        this.associationRepository = associationRepository;
        this.hoursRepository = hoursRepository;
        this.invitationRepository = invitationRepository;
        this.slugGenerator = slugGenerator;
        this.ghostService = ghostService;
    }

    @Transactional(readOnly = true)
    public ShopEntity getById(UUID id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));
    }

    @Transactional(readOnly = true)
    public ShopEntity getBySlug(String slug) {
        return shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));
    }

    /** D34: Creator becomes first OWNER via ShopMembership. */
    @Transactional
    public ShopEntity createShop(UUID userId, String name, String timezone) {
        validateTimezone(timezone);

        var shop = new ShopEntity(name, slugGenerator.generate(name), timezone);
        shop = shopRepository.saveAndFlush(shop);

        var membership = new ShopMembershipEntity(shop.getId(), userId, ShopRole.OWNER);
        membershipRepository.save(membership);

        log.info("Shop created: id={} name={} owner={}", shop.getId(), name, userId);
        return shop;
    }

    /** D40: DRAFT -> ACTIVE requires address + at least one OWNER. */
    @Transactional
    public ShopEntity activateShop(UUID shopId, UUID userId) {
        ShopEntity shop = getById(shopId);
        requireRole(shopId, userId, ShopRole.OWNER);

        if (shop.getStatus() != ShopStatus.DRAFT && shop.getStatus() != ShopStatus.SUSPENDED) {
            throw new IllegalStateException("Shop can only be activated from DRAFT or SUSPENDED status");
        }
        if (shop.getAddressLine1() == null || shop.getCity() == null) {
            throw new IllegalArgumentException("Address required to activate shop");
        }

        shop.setStatus(ShopStatus.ACTIVE);
        log.info("Shop activated: id={} by={}", shopId, userId);
        return shopRepository.save(shop);
    }

    @Transactional
    public ShopEntity suspendShop(UUID shopId, UUID userId) {
        ShopEntity shop = getById(shopId);
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);

        if (shop.getStatus() != ShopStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE shops can be suspended");
        }

        shop.setStatus(ShopStatus.SUSPENDED);
        log.info("Shop suspended: id={} by={}", shopId, userId);
        return shopRepository.save(shop);
    }

    /** D40: CLOSE as terminal lifecycle state — full cleanup like deleteShop minus soft-delete flag. */
    @Transactional
    public ShopEntity closeShop(UUID shopId, UUID userId) {
        ShopEntity shop = getById(shopId);
        requireRole(shopId, userId, ShopRole.OWNER);

        if (shop.getStatus() == ShopStatus.CLOSED) {
            throw new IllegalStateException("Shop is already closed");
        }

        var now = Instant.now();
        associationRepository.endAllActiveByShopId(shopId, now, userId);
        seatRepository.removeAllByShopId(shopId, now);
        membershipRepository.revokeAllByShopId(shopId, now, userId);
        hoursRepository.deleteByShopId(shopId);
        cancelPendingInvitations(shopId);

        shop.setStatus(ShopStatus.CLOSED);
        log.info("Shop closed: id={} by={}", shopId, userId);
        return shopRepository.save(shop);
    }

    /** D43: Soft delete with full cascade. */
    @Transactional
    public void deleteShop(UUID shopId, UUID userId) {
        ShopEntity shop = getById(shopId);
        requireRole(shopId, userId, ShopRole.OWNER);

        var now = Instant.now();

        associationRepository.endAllActiveByShopId(shopId, now, userId);
        seatRepository.removeAllByShopId(shopId, now);
        membershipRepository.revokeAllByShopId(shopId, now, userId);
        hoursRepository.deleteByShopId(shopId);
        cancelPendingInvitations(shopId);

        shop.setStatus(ShopStatus.CLOSED);
        shop.setDeleted(true);
        shop.setDeletedAt(now);
        // Mangle slug to free namespace for future shops with same name
        shop.setSlug(shop.getSlug() + "-deleted-" + shop.getId().toString().substring(0, 8));
        shopRepository.save(shop);

        log.info("Shop deleted: id={} by={}", shopId, userId);
    }

    @Transactional
    public ShopEntity updateShop(UUID shopId, UUID userId, String name, String description,
                                  String phoneNumber, String email, String website,
                                  String addressLine1, String addressLine2, String city,
                                  String stateRegion, String postalCode, String countryCode,
                                  String formattedAddress, Double latitude, Double longitude) {
        ShopEntity shop = getById(shopId);
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);

        if (name != null) {
            if (name.isBlank()) {
                throw new IllegalArgumentException("Shop name cannot be blank");
            }
            shop.setName(name);
        }
        if (description != null) shop.setDescription(description.isBlank() ? null : description);
        if (phoneNumber != null) shop.setPhoneNumber(phoneNumber.isBlank() ? null : phoneNumber);
        if (email != null) shop.setEmail(email.isBlank() ? null : email);
        if (website != null) shop.setWebsite(website.isBlank() ? null : website);
        if (addressLine1 != null) shop.setAddressLine1(addressLine1);
        if (addressLine2 != null) shop.setAddressLine2(addressLine2.isBlank() ? null : addressLine2);
        if (city != null) shop.setCity(city);
        if (stateRegion != null) shop.setStateRegion(stateRegion.isBlank() ? null : stateRegion);
        if (postalCode != null) shop.setPostalCode(postalCode.isBlank() ? null : postalCode);
        if (countryCode != null) shop.setCountryCode(countryCode);
        if (formattedAddress != null) shop.setFormattedAddress(formattedAddress);
        if (latitude != null) shop.setLatitude(latitude);
        if (longitude != null) shop.setLongitude(longitude);

        return shopRepository.save(shop);
    }

    // --- Membership ---

    /** D42: Block removal of last OWNER. */
    @Transactional
    public void removeMembership(UUID shopId, UUID targetUserId, UUID actorUserId) {
        requireRole(shopId, actorUserId, ShopRole.OWNER);

        ShopMembershipEntity membership = membershipRepository
                .findByShopIdAndUserIdAndStatus(shopId, targetUserId, MembershipStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found"));

        if (membership.getRole() == ShopRole.OWNER) {
            List<ShopMembershipEntity> owners = membershipRepository.findByShopIdAndRoleAndStatusForUpdate(
                    shopId, ShopRole.OWNER, MembershipStatus.ACTIVE);
            if (owners.size() <= 1) {
                throw new IllegalStateException("Cannot remove the last owner. Close the shop instead.");
            }
        }

        membership.setStatus(MembershipStatus.REVOKED);
        membership.setRevokedAt(Instant.now());
        membership.setRevokedByUserId(actorUserId);
        membershipRepository.save(membership);

        log.info("Membership revoked: shop={} target={} by={}", shopId, targetUserId, actorUserId);
    }

    @Transactional
    public ShopMembershipEntity addMembership(UUID shopId, UUID targetUserId, ShopRole role, UUID actorUserId) {
        requireRole(shopId, actorUserId, ShopRole.OWNER);
        getById(shopId);

        if (membershipRepository.existsByShopIdAndUserIdAndStatus(shopId, targetUserId, MembershipStatus.ACTIVE)) {
            throw new IllegalStateException("User already has an active membership at this shop");
        }

        // Handle re-add after revocation: delete + flush before insert (Hibernate flushes INSERTs before DELETEs)
        membershipRepository.findByShopIdAndUserIdAndStatus(shopId, targetUserId, MembershipStatus.REVOKED)
                .ifPresent(revoked -> {
                    membershipRepository.delete(revoked);
                    membershipRepository.flush();
                });

        var membership = new ShopMembershipEntity(shopId, targetUserId, role);
        membership = membershipRepository.saveAndFlush(membership);
        log.info("Membership added: shop={} user={} role={} by={}", shopId, targetUserId, role, actorUserId);
        return membership;
    }

    @Transactional(readOnly = true)
    public List<ShopMembershipEntity> getMemberships(UUID shopId) {
        return membershipRepository.findByShopIdAndStatus(shopId, MembershipStatus.ACTIVE);
    }

    // --- Seats ---

    @Transactional
    public SeatEntity addSeat(UUID shopId, UUID userId, String seatName) {
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);
        getById(shopId);

        var seat = new SeatEntity(shopId, seatName);
        seat = seatRepository.saveAndFlush(seat);
        log.info("Seat added: shop={} seat={} by={}", shopId, seat.getId(), userId);
        return seat;
    }

    @Transactional
    public void removeSeat(UUID shopId, UUID seatId, UUID userId) {
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);

        SeatEntity seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        if (!seat.getShopId().equals(shopId)) {
            throw new ResourceNotFoundException("Seat not found");
        }

        seat.setStatus(SeatStatus.REMOVED);
        seat.setRemovedAt(Instant.now());
        seatRepository.save(seat);

        // Clear default_seat_id on active associations referencing this seat
        for (var assoc : associationRepository.findByShopIdAndLeftAtIsNull(shopId)) {
            if (seatId.equals(assoc.getDefaultSeatId())) {
                assoc.setDefaultSeatId(null);
                associationRepository.save(assoc);
            }
        }

        log.info("Seat removed: shop={} seat={} by={}", shopId, seatId, userId);
    }

    @Transactional(readOnly = true)
    public List<SeatEntity> getSeats(UUID shopId) {
        return seatRepository.findByShopIdAndStatusNot(shopId, SeatStatus.REMOVED);
    }

    // --- Barber Associations ---

    /** Validates shop + seat ownership, then creates association. */
    @Transactional
    public BarberShopAssociationEntity addBarberToShop(UUID shopId, UUID barberId, UUID seatId) {
        getById(shopId); // verify shop exists and not deleted

        if (associationRepository.existsByBarberIdAndShopIdAndLeftAtIsNull(barberId, shopId)) {
            throw new IllegalStateException("Barber already has an active association with this shop");
        }

        // Validate seatId belongs to this shop and is active
        if (seatId != null) {
            SeatEntity seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
            if (!seat.getShopId().equals(shopId)) {
                throw new IllegalArgumentException("Seat does not belong to this shop");
            }
            if (seat.getStatus() != SeatStatus.ACTIVE) {
                throw new IllegalArgumentException("Seat is not active");
            }
        }

        var association = new BarberShopAssociationEntity(barberId, shopId);
        if (seatId != null) {
            association.setDefaultSeatId(seatId);
        }
        association = associationRepository.saveAndFlush(association);
        log.info("Barber associated: shop={} barber={}", shopId, barberId);
        return association;
    }

    /** D39+D41: Ghost barber creation + association in single transaction. */
    @Transactional
    public BarberShopAssociationEntity addGhostBarber(UUID shopId, UUID userId, String email, String name, UUID seatId) {
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);
        ProfileEntity ghostProfile = ghostService.createGhostBarber(userId, email, name);
        return addBarberToShop(shopId, ghostProfile.getId(), seatId);
    }

    @Transactional
    public void removeBarber(UUID shopId, UUID barberId, UUID actorUserId, EndedReason reason) {
        BarberShopAssociationEntity association = associationRepository
                .findByBarberIdAndShopIdAndLeftAtIsNull(barberId, shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Active association not found"));

        association.setLeftAt(Instant.now());
        association.setEndedReason(reason);
        association.setEndedByUserId(actorUserId);
        associationRepository.save(association);

        log.info("Barber removed: shop={} barber={} reason={} by={}", shopId, barberId, reason, actorUserId);
    }

    @Transactional(readOnly = true)
    public List<BarberShopAssociationEntity> getActiveAssociations(UUID shopId) {
        return associationRepository.findByShopIdAndLeftAtIsNull(shopId);
    }

    // --- Invitations (D41) ---

    @Transactional
    public ShopBarberInvitationEntity inviteBarber(UUID shopId, UUID barberId, UUID inviterUserId) {
        requireRole(shopId, inviterUserId, ShopRole.OWNER, ShopRole.MANAGER);
        getById(shopId);

        if (invitationRepository.existsPendingNonExpired(
                shopId, barberId, InvitationStatus.PENDING, Instant.now())) {
            throw new IllegalStateException("Invitation already pending for this barber");
        }

        if (associationRepository.existsByBarberIdAndShopIdAndLeftAtIsNull(barberId, shopId)) {
            throw new IllegalStateException("Barber already has an active association with this shop");
        }

        var invitation = new ShopBarberInvitationEntity(shopId, barberId, inviterUserId);
        invitation.setExpiresAt(Instant.now().plus(Duration.ofDays(7)));
        invitation = invitationRepository.saveAndFlush(invitation);

        log.info("Barber invited: shop={} barber={} by={}", shopId, barberId, inviterUserId);
        return invitation;
    }

    /**
     * P0 fix: Validates identity (barberProfileId must match invitation target),
     * checks expiry, and creates association atomically.
     */
    @Transactional
    public BarberShopAssociationEntity acceptInvitation(UUID invitationId, UUID barberProfileId) {
        ShopBarberInvitationEntity invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        // P0 fix: verify the authenticated user is the invited barber
        if (!invitation.getBarberId().equals(barberProfileId)) {
            throw new AccessDeniedException("This invitation is not addressed to you");
        }

        // P0 fix: check expiry (status transition removed — IllegalStateException rolls back @Transactional)
        if (invitation.getExpiresAt() != null && invitation.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Invitation has expired");
        }

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setRespondedAt(Instant.now());
        invitationRepository.save(invitation);

        // Create association atomically within same transaction
        var association = addBarberToShop(invitation.getShopId(), invitation.getBarberId(), null);
        log.info("Invitation accepted: id={} barber={} shop={}", invitationId, barberProfileId, invitation.getShopId());
        return association;
    }

    @Transactional
    public void declineInvitation(UUID invitationId, UUID barberProfileId) {
        ShopBarberInvitationEntity invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found"));

        if (!invitation.getBarberId().equals(barberProfileId)) {
            throw new AccessDeniedException("This invitation is not addressed to you");
        }

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        invitation.setStatus(InvitationStatus.DECLINED);
        invitation.setRespondedAt(Instant.now());
        invitationRepository.save(invitation);
    }

    private void cancelPendingInvitations(UUID shopId) {
        for (var inv : invitationRepository.findByShopIdAndStatus(shopId, InvitationStatus.PENDING)) {
            inv.setStatus(InvitationStatus.CANCELED);
            inv.setRespondedAt(Instant.now());
            invitationRepository.save(inv);
        }
    }

    // --- Operating Hours ---

    @Transactional(readOnly = true)
    public List<ShopOperatingHoursEntity> getHours(UUID shopId) {
        return hoursRepository.findByShopIdOrderByDayOfWeekAscOpensAtAsc(shopId);
    }

    @Transactional
    public List<ShopOperatingHoursEntity> setHours(UUID shopId, UUID userId,
                                                    List<HoursInput> hours) {
        requireRole(shopId, userId, ShopRole.OWNER, ShopRole.MANAGER);
        hoursRepository.deleteByShopId(shopId);
        return hours.stream().map(h -> hoursRepository.save(
                new ShopOperatingHoursEntity(shopId, h.dayOfWeek(), h.opensAt(), h.closesAt())
        )).toList();
    }

    public record HoursInput(short dayOfWeek, LocalTime opensAt, LocalTime closesAt) {}

    // --- Authorization ---

    @Transactional(readOnly = true)
    public void requireRole(UUID shopId, UUID userId, ShopRole... allowedRoles) {
        ShopMembershipEntity membership = membershipRepository
                .findByShopIdAndUserIdAndStatus(shopId, userId, MembershipStatus.ACTIVE)
                .orElseThrow(() -> new AccessDeniedException("Not a member of this shop"));

        for (ShopRole allowed : allowedRoles) {
            if (membership.getRole() == allowed) {
                return;
            }
        }
        throw new AccessDeniedException("Insufficient permissions for this action");
    }

    private static void validateTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid timezone: " + timezone);
        }
        if (!timezone.contains("/")) {
            throw new IllegalArgumentException(
                    "Use an IANA region timezone (e.g., 'America/New_York'), not an offset");
        }
    }
}
