package io.aharoj.barbershop_backend.modules.auth.model.entity;

import io.aharoj.barbershop_backend.modules.auth.model.enums.RoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true, nullable = false, length = 20)
  private RoleType roleType;

  public Role() {
  }

  public Role(RoleType roleType) {
    this.roleType = roleType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }


}
