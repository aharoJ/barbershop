package io.aharoj.barbershop_backend.modules.image.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import io.aharoj.barbershop_backend.modules.customer.model.entity.Customer;
import io.aharoj.barbershop_backend.modules.owner.model.entity.Owner;
import io.aharoj.barbershop_backend.modules.shop.model.entity.Shop;

@Entity
@Table(name = "images")
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String fileName; // e.g. "abc123.png"

  @Column(nullable = false)
  private String fileType; // e.g. "image/png"

  @Column(nullable = false)
  private long fileSize;

  private String fileUrl; // e.g. "/api/images/files/abc123.png"

  private LocalDateTime uploadedAt = LocalDateTime.now();

  // references to possible owners, barbers, customers, shops
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private Owner owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "barber_id")
  private Barber barber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id")
  private Shop shop;

  public Image() {
  }

  public Image(String fileName, String fileType, long fileSize, String fileUrl) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileSize = fileSize;
    this.fileUrl = fileUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public LocalDateTime getUploadedAt() {
    return uploadedAt;
  }

  public void setUploadedAt(LocalDateTime uploadedAt) {
    this.uploadedAt = uploadedAt;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Barber getBarber() {
    return barber;
  }

  public void setBarber(Barber barber) {
    this.barber = barber;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

  
}