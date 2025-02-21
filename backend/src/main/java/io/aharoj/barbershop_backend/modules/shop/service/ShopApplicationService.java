package io.aharoj.barbershop_backend.modules.shop.service;

import java.util.List;

import io.aharoj.barbershop_backend.modules.shop.dto.response.ShopApplicationResponse;

public interface ShopApplicationService {

  /**
   * Allows a barber to apply for a position at a shop.
   *
   * @param shopId       the ID of the shop to which the barber is applying
   * @param barberUserId the ID of the barber applying for association
   * @param message      a message from the barber explaining their interest or
   *                     qualifications
   * @return a ShopApplicationResponse containing the details of the newly created
   *         application (status: PENDING)
   * @sidenote This method initiates the application process for a barber, keeping
   *           the initial status as PENDING.
   */
  ShopApplicationResponse barberApplies(Long shopId, Long barberUserId, String message);

  /**
   * Retrieves all shop applications for a specific shop.
   *
   * @param shopId      the ID of the shop for which to retrieve applications
   * @param ownerUserId the ID of the owner making the request, ensuring proper
   *                    authorization
   * @return a list of ShopApplicationResponse objects representing all
   *         applications for the shop
   * @sidenote This method ensures that only authorized owners can access and
   *           review the shop's application data.
   */
  List<ShopApplicationResponse> getApplications(Long shopId, Long ownerUserId);

  /**
   * Approves a shop application, thereby creating a barber-shop association.
   *
   * @param shopId        the ID of the shop for which the application is being
   *                      approved
   * @param applicationId the ID of the application to approve
   * @param ownerUserId   the ID of the owner approving the application for
   *                      authorization purposes
   * @return a ShopApplicationResponse reflecting the updated status and
   *         association details (status: APPROVED)
   * @sidenote This method transitions an application from a pending state to
   *           approved, establishing a barber-shop association.
   */
  ShopApplicationResponse approveApplication(Long shopId, Long applicationId, Long ownerUserId);

  /**
   * Rejects a shop application.
   *
   * @param shopId        the ID of the shop for which the application is being
   *                      rejected
   * @param applicationId the ID of the application to reject
   * @param ownerUserId   the ID of the owner rejecting the application, ensuring
   *                      proper authorization
   * @return a ShopApplicationResponse reflecting the updated status (status:
   *         REJECTED)
   * @sidenote This method updates the application status to REJECTED, informing
   *           the applicant of the decision.
   */
  ShopApplicationResponse rejectApplication(Long shopId, Long applicationId, Long ownerUserId);
}
