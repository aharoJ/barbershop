package io.aharoj.barbershop_backend.modules.auth.dto.response;



/**
 * MessageResponse
 */
public class MessageResponse {
  private String message;

  public MessageResponse() {
  }

  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
