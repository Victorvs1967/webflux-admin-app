package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
  Flux<UserDto> getUsers();
  Mono<UserDto> getUser(String username);
  Mono<UserDto> updateUserData(UserDto userDto);
  Mono<Void> deleteUser(String username);
}
