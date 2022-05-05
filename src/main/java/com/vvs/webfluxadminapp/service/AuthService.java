package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.dto.ResponseDto;
import com.vvs.webfluxadminapp.dto.UserDto;

import reactor.core.publisher.Mono;

public interface AuthService {
  Mono<UserDto> signUp(UserDto userDto);
  Mono<ResponseDto> login(String username, String password);
}
