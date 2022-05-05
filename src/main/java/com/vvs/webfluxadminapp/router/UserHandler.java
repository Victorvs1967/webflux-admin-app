package com.vvs.webfluxadminapp.router;

import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.security.JwtUtil;
import com.vvs.webfluxadminapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {

  @Autowired
  private UserService userService;
  @Autowired
  private JwtUtil jwtUtil;

  public Mono<ServerResponse> getUsers(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    return jwtUtil.getAllClaimsFromToken(token)
      .flatMap(credentials -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(userService.getUsers(), UserDto.class));
  }

  public Mono<ServerResponse> getUser(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String username = request.pathVariable("username");
    return jwtUtil.getAllClaimsFromToken(token)
      .flatMap(cradentials -> ServerResponse
      .ok()
      .contentType(APPLICATION_JSON)
      .body(userService.getUser(username), UserDto.class));
  }

  public Mono<ServerResponse> updateUserData(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    Mono<UserDto> userDto = request.bodyToMono(UserDto.class);
    return jwtUtil.getAllClaimsFromToken(token)
      .flatMap(credentials -> userDto
        .map(userService::updateUserData)
        .flatMap(user -> ServerResponse
          .ok()
          .contentType(APPLICATION_JSON)
          .body(user, UserDto.class)));
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String username = request.pathVariable("username");
    return jwtUtil.getAllClaimsFromToken(token)
      .flatMap(credentials -> ServerResponse
        .noContent()
        .build(userService.deleteUser(username)));
  }
  
}
