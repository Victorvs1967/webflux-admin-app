package com.vvs.webfluxadminapp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vvs.webfluxadminapp.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
  
  private String id;
  private String username;
  // @JsonIgnore
  private String password;
  private String email;

  private String firstName;
  private String lastName;
  private String phone;
  private String address;

  private Date onCreate;
  private Date onUpdate;
  private boolean isActive;
  private UserRole role;

  public String fullName() {
    return firstName != null ? firstName.concat(" ").concat(lastName) : "";
  }
}
