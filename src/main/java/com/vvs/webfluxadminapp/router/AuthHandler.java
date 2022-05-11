package com.vvs.webfluxadminapp.router;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.vvs.webfluxadminapp.dto.LoginDto;
import com.vvs.webfluxadminapp.dto.ResponseDto;
import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AuthHandler {
  
  @Autowired
  private AuthService authService;

  public Mono<ServerResponse> signUp(ServerRequest request) {
    Mono<UserDto> userDto = request.bodyToMono(UserDto.class)
      .flatMap(credentials -> authService.signUp(credentials))
      .map(userDetails -> userDetails);

    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(userDto, UserDto.class);
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<ResponseDto> response = request.bodyToMono(LoginDto.class)
      .flatMap(credentials -> authService.login(credentials.getUsername(), credentials.getPassword()));

    return ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(response, ResponseDto.class);
  }
}
