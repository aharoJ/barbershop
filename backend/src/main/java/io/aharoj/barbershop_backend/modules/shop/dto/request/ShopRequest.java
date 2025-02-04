package io.aharoj.barbershop_backend.modules.shop.dto.request;

public class ShopRequest {
  private String name;
  private String address;

  public ShopRequest() {
  }

  public ShopRequest(String name, String address) {
    this.name = name;
    this.address = address;
  }

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

}
