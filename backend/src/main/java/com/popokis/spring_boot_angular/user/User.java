package com.popokis.spring_boot_angular.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "users")
@Data
@Builder(toBuilder = true)
@JsonDeserialize(builder = User.UserBuilder.class)
@RequiredArgsConstructor(staticName = "create")
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class User implements GrantedAuthority {

  @Id
  private String id;

  @NonNull
  @NotNull
  @NotBlank
  @Size(max = 1024)
  @Email(message = "Invalid email")
  @Indexed(unique = true)
  private String email;

  @NonNull
  @NotNull
  @NotBlank
  @Size(max = 4096)
  private String password;

  @NonNull
  @NotNull
  @NotBlank
  @Size(max = 1024)
  private String name;

  @NonNull
  @NotNull
  @NotBlank
  @Size(max = 1024)
  private String surname;

  @Builder.Default
  private boolean active = true;

  @NonNull
  @NotNull
  private Role role;

  @JsonIgnore
  @Override
  public String getAuthority() {
    return "ROLE_" + getRole();
  }

  public enum Role {
    ADMIN, USER
  }
}
