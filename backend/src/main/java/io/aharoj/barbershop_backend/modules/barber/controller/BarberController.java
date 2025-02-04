package io.aharoj.barbershop_backend.modules.barber.controller;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.barber.dto.request.BarberProfileRequest;
import io.aharoj.barbershop_backend.modules.barber.dto.response.BarberProfileResponse;
import io.aharoj.barbershop_backend.modules.barber.service.BarberService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    /**
     * Create a BarberProfile for the currently logged-in user
     * who must have ROLE_BARBER.
     */
    @PostMapping("/profile")
    @PreAuthorize("hasRole('BARBER')")
    public BarberProfileResponse createBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BarberProfileRequest request) {
        // We have a user who has ROLE_BARBER, so create the profile
        return barberService.createBarberProfile(userDetails.getId(), request);
    }

    /**
     * Get the BarberProfile for the currently logged-in barber.
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('BARBER')")
    public BarberProfileResponse getBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return barberService.getBarberProfileByUserId(userDetails.getId());
    }

    /**
     * Update the BarberProfile for the logged-in barber.
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('BARBER')")
    public BarberProfileResponse updateBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BarberProfileRequest request) {
        return barberService.updateBarberProfile(userDetails.getId(), request);
    }
}
