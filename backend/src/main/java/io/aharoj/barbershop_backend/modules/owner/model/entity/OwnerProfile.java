package io.aharoj.barbershop_backend.modules.owner.model.entity;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "owner_profile")
public class OwnerProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private User user;

  @Column(length = 100)
  private String ownerName;

  public OwnerProfile() {
  }

  public OwnerProfile(User user, String ownerName) {
    this.user = user;
    this.ownerName = ownerName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

}
