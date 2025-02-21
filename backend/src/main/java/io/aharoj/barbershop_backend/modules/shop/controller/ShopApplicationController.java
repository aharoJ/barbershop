package io.aharoj.barbershop_backend.modules.shop.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.shop.dto.request.ShopApplicationRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.response.ShopApplicationResponse;
import io.aharoj.barbershop_backend.modules.shop.service.ShopApplicationService;

@RestController
@RequestMapping("/api/shops/{shopId}/applications")
public class ShopApplicationController {

    private final ShopApplicationService applicationService;

    public ShopApplicationController(ShopApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Barber applies to a shop.
     * The body can contain a 'message' if desired.
     */
    @PostMapping
    @PreAuthorize("hasRole('BARBER')")
    public ShopApplicationResponse applyToShop(
            @PathVariable Long shopId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ShopApplicationRequest request) {
        return applicationService.barberApplies(shopId, userDetails.getId(), request.getMessage());
    }

    /**
     * Owner sees all applications for this shop.
     */
    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public List<ShopApplicationResponse> listApplications(
            @PathVariable Long shopId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return applicationService.getApplications(shopId, userDetails.getId());
    }

    /**
     * Owner approves an application => create a BarberShopAssociation
     */
    @PostMapping("/{applicationId}/approve")
    @PreAuthorize("hasRole('OWNER')")
    public ShopApplicationResponse approve(
            @PathVariable Long shopId,
            @PathVariable Long applicationId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return applicationService.approveApplication(shopId, applicationId, userDetails.getId());
    }

    /**
     * Owner rejects an application => status=REJECTED
     */
    @PostMapping("/{applicationId}/reject")
    @PreAuthorize("hasRole('OWNER')")
    public ShopApplicationResponse reject(
            @PathVariable Long shopId,
            @PathVariable Long applicationId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return applicationService.rejectApplication(shopId, applicationId, userDetails.getId());
    }
}
