package io.aharoj.barbershop_backend.modules.shared.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String country;
  private boolean isPrimary;

  public Address() {
  }

  public Address(String street, String city, String state, String postalCode, String country, boolean isPrimary) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.country = country;
    this.isPrimary = isPrimary;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setPrimary(boolean isPrimary) {
    this.isPrimary = isPrimary;
  }

}
