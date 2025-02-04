package io.aharoj.barbershop_backend.modules.auth.model.entity;



import io.aharoj.barbershop_backend.modules.auth.model.enums.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true, nullable = false, length = 20)
  private RoleName roleName;

  public Role() {
  }

  public Role(RoleName roleName) {
    this.roleName = roleName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleName getRoleName() {
    return roleName;
  }

  public void setRoleName(RoleName roleName) {
    this.roleName = roleName;
  }

}
