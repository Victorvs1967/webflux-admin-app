package com.vvs.webfluxadminapp.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.webfluxadminapp.dto.ProjectDto;
import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.model.Project;
import com.vvs.webfluxadminapp.security.JwtUtil;
import com.vvs.webfluxadminapp.service.ImageService;
import com.vvs.webfluxadminapp.service.ProjectService;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

@Component
public class ProjectHandler {

  @Autowired
  private ProjectService projectService;
  @Autowired
  private ImageService imageService;
  @Autowired
  private JwtUtil jwtUtil;

  public Mono<ServerResponse> getProjects(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(projectService.getProjects(), Project.class);
  }

  public Mono<ServerResponse> getProject(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String id = request.pathVariable("id");
    return jwtUtil.validateToken(token)
      .switchIfEmpty(Mono.error(WrongCredentialException::new))
      .map(result -> id)
      .map(projectService::getProject)
      .flatMap(projectDto -> ServerResponse
          .ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(projectDto, ProjectDto.class));
  }

  public Mono<ServerResponse> createProject(ServerRequest request) {
    return request.bodyToMono(ProjectDto.class)
      .map(projectService::createProject)
      .flatMap(projectDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectDto, ProjectDto.class));
  }

  public Mono<ServerResponse> editProject(ServerRequest request) {
    return request.bodyToMono(ProjectDto.class)
      .map(projectService::updateProject)
      .flatMap(projectDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectDto, ProjectDto.class));
  }

  public Mono<ServerResponse> deleteProject(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String id = request.pathVariable("id");
    return jwtUtil.validateToken(token)
      .switchIfEmpty(Mono.error(WrongCredentialException::new))
      .map(result -> id)
      .map(projectService::deleteProject)
      .flatMap(projectDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectDto, ProjectDto.class));
  }

  public Mono<ServerResponse> uploadImg(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
      .map(imageService::upload)
      .flatMap(id -> ServerResponse
        .ok()
        .body(id, Map.class));
  }

  public Mono<ServerResponse> downloadImg(ServerRequest request) {
    return imageService.download(request.pathVariable("id"))
      .flatMap(img -> ServerResponse
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(img, DefaultDataBuffer.class));
  }

  public Mono<ServerResponse> listImg(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(imageService.listFiles(), ArrayList.class);
  }

  public Mono<ServerResponse> deleteImg(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(imageService.delete(request.pathVariable("id")), Void.class);
  }
}
