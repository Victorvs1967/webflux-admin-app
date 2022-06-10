package com.vvs.webfluxadminapp.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.model.Project;
import com.vvs.webfluxadminapp.security.JwtUtil;
import com.vvs.webfluxadminapp.service.ProjectService;

import reactor.core.publisher.Mono;

@Component
public class ProjectHandler {
  
  @Autowired
  private ProjectService projectService;
  @Autowired
  private JwtUtil jwtUtil;

  public Mono<ServerResponse> getProjects(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(projectService.getProjects(), Project.class);
  }

  public Mono<ServerResponse> createProject(ServerRequest request) {
    Mono<Project> project = request.bodyToMono(Project.class)
      .flatMap(projectService::createProject);

    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(project, Project.class);
  }

  public Mono<ServerResponse> deleteProject(ServerRequest request) {
    String token = request.headers().firstHeader("authorization").substring(7);
    String id = request.pathVariable("id");
    return jwtUtil.validateToken(token)
        .switchIfEmpty(Mono.error(WrongCredentialException::new))
        .map(result -> !result)
        .map(isProjectId -> id)
        .flatMap(project -> ServerResponse
          .ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(projectService.deleteProject(id), Project.class));
  }


}
