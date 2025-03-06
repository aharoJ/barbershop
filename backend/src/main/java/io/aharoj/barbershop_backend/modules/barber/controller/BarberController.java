package io.aharoj.barbershop_backend.modules.barber.controller;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.barber.dto.request.BarberRequest;
import io.aharoj.barbershop_backend.modules.barber.dto.response.BarberResponse;
import io.aharoj.barbershop_backend.modules.barber.service.BarberService;

import org.springframework.http.ResponseEntity;
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
    @PostMapping("/me")
    @PreAuthorize("hasRole('BARBER')")
    public BarberResponse createBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BarberRequest request) {
        // We have a user who has ROLE_BARBER, so create the profile
        return barberService.createBarberProfile(userDetails.getId(), request);
    }

    /**
     * Get the BarberProfile for the currently logged-in barber.
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('BARBER')")
    public BarberResponse getBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return barberService.getBarberProfileByUserId(userDetails.getId());
    }

    /**
     * Update the BarberProfile for the logged-in barber.
     */
    @PutMapping("/me")
    @PreAuthorize("hasRole('BARBER')")
    public BarberResponse updateBarberProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BarberRequest request) {
        return barberService.updateBarberProfile(userDetails.getId(), request);
    }

    /**
     * (Optional) Get any barber's profile by ID—if needed for some business logic.
     * For example, maybe owners can look up barbers.
     * 
     * // Similarly, you could do a PUT /api/barbers/{id} if you want owners to be able to edit barbers, etc.
     */
    @GetMapping("/{barberId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<BarberResponse> getBarberProfileById(@PathVariable Long barberId) {
        BarberResponse response = barberService.getBarberProfileByUserId(barberId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/me")
    @PreAuthorize("hasRole('BARBER')")
    public ResponseEntity<?> deleteBarberProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
      barberService.deleteBarberProfile(userDetails.getId());
      return ResponseEntity.ok("Barber profile deleted successfully :(");
    }

}
