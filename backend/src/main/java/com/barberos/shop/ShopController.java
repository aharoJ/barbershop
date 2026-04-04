package com.barberos.shop;

import com.barberos.identity.BarberEnrollmentService;
import com.barberos.identity.UserResolver;
import com.barberos.shop.internal.ShopService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;
    private final UserResolver userResolver;
    private final BarberEnrollmentService enrollmentService;

    public ShopController(ShopService shopService,
                          UserResolver userResolver,
                          BarberEnrollmentService enrollmentService) {
        this.shopService = shopService;
        this.userResolver = userResolver;
        this.enrollmentService = enrollmentService;
    }

    // --- Shop CRUD ---

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(Authentication authentication,
                                               @Valid @RequestBody CreateShopRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopEntity shop = shopService.createShop(userId, request.name(), request.timezone());
        return ResponseEntity.status(201).body(ShopDTO.from(shop));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getShop(@PathVariable UUID id) {
        ShopEntity shop = shopService.getById(id);
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ShopDTO> getShopBySlug(@PathVariable String slug) {
        ShopEntity shop = shopService.getBySlug(slug);
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopDTO> updateShop(Authentication authentication,
                                               @PathVariable UUID id,
                                               @Valid @RequestBody UpdateShopRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopEntity shop = shopService.updateShop(id, userId, request.name(), request.description(),
                request.phoneNumber(), request.email(), request.website(),
                request.addressLine1(), request.addressLine2(), request.city(),
                request.stateRegion(), request.postalCode(), request.countryCode(),
                request.formattedAddress(), request.latitude(), request.longitude());
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<ShopDTO> activateShop(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopEntity shop = shopService.activateShop(id, userId);
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<ShopDTO> suspendShop(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopEntity shop = shopService.suspendShop(id, userId);
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<ShopDTO> closeShop(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopEntity shop = shopService.closeShop(id, userId);
        return ResponseEntity.ok(ShopDTO.from(shop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.deleteShop(id, userId);
        return ResponseEntity.noContent().build();
    }

    // --- Memberships ---

    @GetMapping("/{id}/memberships")
    public ResponseEntity<List<ShopMembershipDTO>> getMemberships(Authentication authentication,
                                                                    @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.requireRole(id, userId, ShopRole.OWNER, ShopRole.MANAGER, ShopRole.STAFF);
        return ResponseEntity.ok(shopService.getMemberships(id).stream().map(ShopMembershipDTO::from).toList());
    }

    @PostMapping("/{id}/memberships")
    public ResponseEntity<ShopMembershipDTO> addMembership(Authentication authentication,
                                                             @PathVariable UUID id,
                                                             @Valid @RequestBody AddMembershipRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        ShopMembershipEntity m = shopService.addMembership(id, request.userId(), request.role(), userId);
        return ResponseEntity.status(201).body(ShopMembershipDTO.from(m));
    }

    @DeleteMapping("/{id}/memberships/{targetUserId}")
    public ResponseEntity<Void> removeMembership(Authentication authentication,
                                                   @PathVariable UUID id,
                                                   @PathVariable UUID targetUserId) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.removeMembership(id, targetUserId, userId);
        return ResponseEntity.noContent().build();
    }

    // --- Seats ---

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatDTO>> getSeats(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.requireRole(id, userId, ShopRole.OWNER, ShopRole.MANAGER, ShopRole.STAFF);
        return ResponseEntity.ok(shopService.getSeats(id).stream().map(SeatDTO::from).toList());
    }

    @PostMapping("/{id}/seats")
    public ResponseEntity<SeatDTO> addSeat(Authentication authentication,
                                            @PathVariable UUID id,
                                            @Valid @RequestBody AddSeatRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        SeatEntity seat = shopService.addSeat(id, userId, request.name());
        return ResponseEntity.status(201).body(SeatDTO.from(seat));
    }

    @DeleteMapping("/{id}/seats/{seatId}")
    public ResponseEntity<Void> removeSeat(Authentication authentication,
                                            @PathVariable UUID id,
                                            @PathVariable UUID seatId) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.removeSeat(id, seatId, userId);
        return ResponseEntity.noContent().build();
    }

    // --- Barber Associations ---

    /** D39+D41: Ghost auto-associate. Creates ghost barber + associates in single transaction. */
    @PostMapping("/{id}/barbers/ghost")
    public ResponseEntity<Void> addGhostBarber(Authentication authentication,
                                                @PathVariable UUID id,
                                                @Valid @RequestBody GhostBarberRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.addGhostBarber(id, userId, request.email(), request.name(), request.seatId());
        return ResponseEntity.status(201).build();
    }

    /** D41: Invite existing claimed barber. */
    @PostMapping("/{id}/barbers/invite")
    public ResponseEntity<Void> inviteBarber(Authentication authentication,
                                              @PathVariable UUID id,
                                              @Valid @RequestBody InviteBarberRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.inviteBarber(id, request.barberId(), userId);
        return ResponseEntity.status(201).build();
    }

    /** D41: Barber accepts invitation — identity verified via barber profile resolution. */
    @PostMapping("/invitations/{invitationId}/accept")
    public ResponseEntity<Void> acceptInvitation(Authentication authentication,
                                                  @PathVariable UUID invitationId) {
        UUID userId = userResolver.resolveUserId(authentication);
        UUID barberProfileId = enrollmentService.resolveBarberProfileId(userId);
        shopService.acceptInvitation(invitationId, barberProfileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invitations/{invitationId}/decline")
    public ResponseEntity<Void> declineInvitation(Authentication authentication,
                                                    @PathVariable UUID invitationId) {
        UUID userId = userResolver.resolveUserId(authentication);
        UUID barberProfileId = enrollmentService.resolveBarberProfileId(userId);
        shopService.declineInvitation(invitationId, barberProfileId);
        return ResponseEntity.ok().build();
    }

    /** D35: Barber self-removal. P0 fix: resolve userId → barberProfileId. */
    @DeleteMapping("/{id}/barbers/me")
    public ResponseEntity<Void> leaveShop(Authentication authentication, @PathVariable UUID id) {
        UUID userId = userResolver.resolveUserId(authentication);
        UUID barberProfileId = enrollmentService.resolveBarberProfileId(userId);
        shopService.removeBarber(id, barberProfileId, userId, EndedReason.BARBER_LEFT);
        return ResponseEntity.noContent().build();
    }

    /** Manager/Owner removes a barber. */
    @DeleteMapping("/{id}/barbers/{barberId}")
    public ResponseEntity<Void> removeBarber(Authentication authentication,
                                              @PathVariable UUID id,
                                              @PathVariable UUID barberId) {
        UUID userId = userResolver.resolveUserId(authentication);
        shopService.requireRole(id, userId, ShopRole.OWNER, ShopRole.MANAGER);
        shopService.removeBarber(id, barberId, userId, EndedReason.SHOP_REMOVED);
        return ResponseEntity.noContent().build();
    }

    // --- Operating Hours ---

    @GetMapping("/{id}/hours")
    public ResponseEntity<List<OperatingHoursDTO>> getHours(@PathVariable UUID id) {
        shopService.getById(id);
        var hours = shopService.getHours(id);
        return ResponseEntity.ok(hours.stream().map(h ->
                new OperatingHoursDTO(h.getDayOfWeek(), h.getOpensAt(), h.getClosesAt())).toList());
    }

    @PutMapping("/{id}/hours")
    public ResponseEntity<List<OperatingHoursDTO>> setHours(Authentication authentication,
                                                              @PathVariable UUID id,
                                                              @Valid @RequestBody List<SetHoursRequest> hours) {
        UUID userId = userResolver.resolveUserId(authentication);
        var inputs = hours.stream()
                .map(h -> new ShopService.HoursInput(h.dayOfWeek(), h.opensAt(), h.closesAt()))
                .toList();
        var saved = shopService.setHours(id, userId, inputs);
        return ResponseEntity.ok(saved.stream().map(h ->
                new OperatingHoursDTO(h.getDayOfWeek(), h.getOpensAt(), h.getClosesAt())).toList());
    }

    // --- Request/Response records ---

    public record CreateShopRequest(
            @NotBlank @Size(max = 255) String name,
            @NotBlank @Size(max = 50) String timezone
    ) {}

    public record UpdateShopRequest(
            @Size(max = 255) String name,
            @Size(max = 5000) String description,
            @Size(max = 50) String phoneNumber,
            @Email @Size(max = 255) String email,
            @Size(max = 500) String website,
            String addressLine1,
            String addressLine2,
            String city,
            String stateRegion,
            String postalCode,
            @Size(max = 2) String countryCode,
            String formattedAddress,
            Double latitude,
            Double longitude
    ) {}

    public record AddMembershipRequest(@NotNull UUID userId, @NotNull ShopRole role) {}

    public record AddSeatRequest(@NotBlank @Size(max = 100) String name) {}

    public record GhostBarberRequest(
            @NotBlank @Email String email,
            @NotBlank String name,
            UUID seatId
    ) {}

    public record InviteBarberRequest(@NotNull UUID barberId) {}

    /** dayOfWeek: 1=Monday, 7=Sunday (ISO 8601 / java.time.DayOfWeek) */
    public record SetHoursRequest(
            @Min(1) @Max(7) short dayOfWeek,
            @NotNull LocalTime opensAt,
            @NotNull LocalTime closesAt
    ) {}

    public record OperatingHoursDTO(short dayOfWeek, LocalTime opensAt, LocalTime closesAt) {}
}
