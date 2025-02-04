package io.aharoj.barbershop_backend.modules.shared.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.aharoj.barbershop_backend.modules.appointment.model.entity.Appointment;
import io.aharoj.barbershop_backend.modules.barber.dto.request.BarberProfileRequest;
import io.aharoj.barbershop_backend.modules.barber.model.entity.BarberProfile;
import io.aharoj.barbershop_backend.modules.customer.model.entity.CustomerProfile;
import io.aharoj.barbershop_backend.modules.image.dto.request.ImageRequest;
import io.aharoj.barbershop_backend.modules.image.dto.response.ImageResponse;
import io.aharoj.barbershop_backend.modules.image.model.entity.Image;
import io.aharoj.barbershop_backend.modules.payment.dto.request.PaymentRequest;
import io.aharoj.barbershop_backend.modules.payment.dto.response.PaymentResponse;
import io.aharoj.barbershop_backend.modules.payment.model.entity.Payment;
import io.aharoj.barbershop_backend.modules.promotion.dto.request.PromotionRequest;
import io.aharoj.barbershop_backend.modules.promotion.dto.response.PromotionResponse;
import io.aharoj.barbershop_backend.modules.promotion.model.entity.Promotion;
import io.aharoj.barbershop_backend.modules.review.dto.request.ReviewRequest;
import io.aharoj.barbershop_backend.modules.review.dto.response.ReviewResponse;
import io.aharoj.barbershop_backend.modules.review.model.entity.Review;
import io.aharoj.barbershop_backend.modules.shop.model.entity.*;

@Component
public class MapperUtil {

  /**
   * // Customer Mappings
   * public static CustomerProfileResponse toCustomerResponse(CustomerProfile
   * customerProfile) {
   * return new CustomerProfileResponse(
   * customerProfile.getId(),
   * customerProfile.getUser().getUsername(),
   * customerProfile.getUser().getEmail(),
   * customerProfile.getFirstName(),
   * customerProfile.getLastName(),
   * customerProfile.getPhoneNumber());
   * }
   * 
   * public static CustomerProfile toCustomerProfile(CustomerRegistrationRequest
   * request, User user) {
   * CustomerProfile profile = new CustomerProfile();
   * profile.setUser(user);
   * profile.setFirstName(request.getFirstName());
   * profile.setLastName(request.getLastName());
   * profile.setPhoneNumber(request.getPhoneNumber());
   * return profile;
   * }
   */

  /**
   * // OwnerProfile Mappings TEST
   * 
   * public static OwnerProfileResponse toOwnerProfileResponse(OwnerProfile
   * ownerProfile) {
   * return new OwnerProfileResponse(
   * ownerProfile.getId(),
   * ownerProfile.getFirstName(),
   * ownerProfile.getLastName(),
   * ownerProfile.getPhoneNumber());
   * }
   * 
   * public static OwnerProfile toOwnerProfile(OwnerProfileRequest request) {
   * OwnerProfile profile = new OwnerProfile();
   * profile.setFirstName(request.getFirstName());
   * profile.setLastName(request.getLastName());
   * profile.setPhoneNumber(request.getPhoneNumber());
   * // Do NOT set user here; it's handled in the service layer
   * return profile;
   * }
   */

  /**
   * * // BarberProfile Mappings
   * 
   * ublic static BarberProfileRespons
   * turn new BarberProfile
   * barberProfile.getId(),
   * barberProfile.getFirstName()
   * barberProfile.getLastName(),
   * 
   * }
   */

  public static BarberProfile toBarberProfile(BarberProfileRequest request) {
    BarberProfile profile = new BarberProfile();
    profile.setFirstName(request.getFirstName());
    profile.setLastName(request.getLastName());
    profile.setPhoneNumber(request.getPhoneNumber());
    // Assume user association is handled in the service
    return profile;
  }

  // Shop Mappings TEST
  /**
   * public static ShopResponse toShopResponse(Shop shop) {
   * return new ShopResponse(
   * shop.getId(),
   * shop.getName(),
   * shop.getAddress(),
   * shop.getPhoneNumber(),
   * shop.getOwner().getId());
   * }
   * 
   * public static Shop toShop(ShopRequest request, OwnerProfile owner) {
   * Shop shop = new Shop();
   * shop.setName(request.getName());
   * shop.setAddress(request.getAddress());
   * shop.setPhoneNumber(request.getPhoneNumber());
   * shop.setOwner(owner);
   * return shop;
   * }
   */

  // Review Mappings
  public static ReviewResponse toReviewResponse(Review review) {
    // return new ReviewResponse(id, appointmentId, rating, comment, customerId)
    return new ReviewResponse(
        review.getId(),
        review.getShop() != null ? review.getShop().getId() : null, // Shop ID (if applicable)
        review.getBarber() != null ? review.getBarber().getId() : null, // Barber ID (if applicable)
        review.getCustomer().getId(),
        review.getRating(),
        review.getComment());
  }

  public static Review toReview(ReviewRequest request, CustomerProfile customer, Shop shop, BarberProfile barber) {
    Review review = new Review();
    review.setCustomer(customer); // mandatory

    // Set the shop or barber based on the request (optional)
    if (shop != null) {
      review.setShop(shop);
    }
    if (barber != null) {
      review.setBarber(barber);
    }

    review.setRating(request.getRating());
    review.setComment(request.getComment());
    review.setDate(LocalDateTime.now());

    return review;
  }

  // Promotion Mappings
  public static PromotionResponse toPromotionResponse(Promotion promotion) {
    return new PromotionResponse(
        promotion.getId(),
        promotion.getTitle(),
        promotion.getDescription(),
        promotion.getStartDate(),
        promotion.getEndDate(),
        promotion.getShop().getId());
  }

  public static Promotion toPromotion(PromotionRequest request, Shop shop) {
    Promotion promotion = new Promotion();
    promotion.setTitle(request.getTitle());
    promotion.setDescription(request.getDescription());
    promotion.setStartDate(request.getStartDate());
    promotion.setEndDate(request.getEndDate());
    promotion.setShop(shop);
    return promotion;
  }

  // Payment Mappings
  public static PaymentResponse toPaymentResponse(Payment payment) {
    return new PaymentResponse(
        payment.getId(),
        payment.getAppointment().getId(),
        payment.getAmount(),
        payment.getPaymentMethod(),
        payment.getPaymentStatus());
  }

  public static Payment toPayment(PaymentRequest request, Appointment appointment) {
    Payment payment = new Payment();

    payment.setAppointment(appointment);
    payment.setAmount(request.getAmount());
    payment.setPaymentMethod(request.getPaymentMethod());
    payment.setPaymentStatus(request.getPaymentStatus());

    return payment;
  }

  // Image Mappings
  public static ImageResponse toImageResponse(Image image) {
    Long entityId = null;
    String entityType = null;

    if (image.getShop() != null) {
      entityId = image.getShop().getId();
      entityType = "SHOP";
    } else if (image.getBarber() != null) {
      entityId = image.getBarber().getId();
      entityType = "BARBER";
    } else if (image.getCustomer() != null) {
      entityId = image.getCustomer().getId();
      entityType = "CUSTOMER";
    }

    return new ImageResponse(
        image.getId(),
        image.getImageUrl(),
        entityId,
        entityType);
  }

  public static Image toImage(ImageRequest request, Shop shop, BarberProfile barber, CustomerProfile customer) {
    Image image = new Image();
    image.setImageUrl(request.getImageUrl());

    // Dynamically assign the relationship based on entityType
    String entityType = request.getEntityType().toUpperCase();
    switch (entityType) {
      case "SHOP":
        image.setShop(shop); // Pass the Shop object
        break;
      case "BARBER":
        image.setBarber(barber); // Pass the BarberProfile object
        break;
      case "CUSTOMER":
        image.setCustomer(customer); // Pass the CustomerProfile object
        break;
      default:
        throw new IllegalArgumentException("Invalid entityType provided: " + entityType);
    }

    return image;
  }
}
