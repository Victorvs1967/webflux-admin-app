package com.vvs.webfluxadminapp.mapper;

import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.model.User;

public interface UserMapper {
  
  public UserDto toDto(User user);
  public User fromDto(UserDto userDto);
}
