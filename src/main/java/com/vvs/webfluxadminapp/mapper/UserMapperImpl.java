package com.vvs.webfluxadminapp.mapper;

import java.time.Instant;
import java.util.Date;

import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.model.User;
import com.vvs.webfluxadminapp.model.UserRole;

import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDto toDto(User user) {
    return UserDto.builder()
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .phone(user.getPhone())
      .address(user.getAddress())
      .photo(user.getPhoto())
      .avatar(user.getAvatar())
      .onCreate(user.getOnCreate())
      .onUpdate(user.getOnUpdate())
      .isActive(user.isActive())
      .role(user.getRole())
      .build();
  }

  @Override
  public User fromDto(UserDto userDto) {
    return User.builder()
      .username(userDto.getUsername())
      .password(userDto.getPassword())
      .email(userDto.getEmail())
      .firstName(userDto.getFirstName())
      .lastName(userDto.getLastName())
      .phone(userDto.getPhone())
      .address(userDto.getAddress())
      .photo(userDto.getPhoto())
      .avatar(userDto.getAvatar())
      .onCreate(userDto.getOnCreate() != null ? userDto.getOnCreate() : Date.from(Instant.now()))
      .onUpdate(Date.from(Instant.now()))
      .isActive(true)
      .role(userDto.getRole() != null ? userDto.getRole() : UserRole.USER)
      .build();
  }
  
}
