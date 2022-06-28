package com.vvs.webfluxadminapp.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.webfluxadminapp.dto.SkillDto;
import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.security.JwtUtil;
import com.vvs.webfluxadminapp.service.SkillService;

import reactor.core.publisher.Mono;

@Component
public class SkillHandler {
  
  @Autowired
  private SkillService skillService;
  @Autowired
  private JwtUtil jwtUtil;
  
  public Mono<ServerResponse> getSkills(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(skillService.getSkills(), SkillDto.class);
  }

  public Mono<ServerResponse> getSkill(ServerRequest request) {
    return request.bodyToMono(SkillDto.class)
      .map(skillService::updateSkill)
      .flatMap(skillDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(skillDto, SkillDto.class));
  }

  public Mono<ServerResponse> createSkill(ServerRequest request) {
    return request.bodyToMono(SkillDto.class)
      .map(skillService::createSkill)
      .flatMap(skillDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(skillDto, SkillDto.class));
  }

  public Mono<ServerResponse> updateSkill(ServerRequest request) {
    return request.bodyToMono(SkillDto.class)
      .map(skillService::updateSkill)
      .flatMap(skillDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(skillDto, SkillDto.class));
  }

  public Mono<ServerResponse> deleteSkill(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String id = request.pathVariable("id");
    return jwtUtil.validateToken(token)
      .switchIfEmpty(Mono.error(WrongCredentialException::new))
      .map(result -> id)
      .map(skillService::deleteSkill)
      .flatMap(skillDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(skillDto, SkillDto.class));
  }
}
