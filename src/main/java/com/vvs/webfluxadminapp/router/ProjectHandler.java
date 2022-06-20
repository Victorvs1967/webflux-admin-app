package com.vvs.webfluxadminapp.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.webfluxadminapp.dto.ProjectDto;
import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.model.Project;
import com.vvs.webfluxadminapp.security.JwtUtil;
import com.vvs.webfluxadminapp.service.ProjectService;

import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class ProjectHandler {

  @Autowired
  private ProjectService projectService;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private ReactiveGridFsTemplate gridFsTemplate;

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
        .map(result -> !result)
        .map(isId -> id)
        .map(projectService::getProject)
        .flatMap(project -> ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(project, ProjectDto.class));
  }

  public Mono<ServerResponse> createProject(ServerRequest request) {
    return request.bodyToMono(ProjectDto.class)
      .map(projectService::createProject)
      .flatMap(project -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(project, ProjectDto.class));
  }

  public Mono<ServerResponse> editProject(ServerRequest request) {
    return request.bodyToMono(ProjectDto.class)
      .map(projectService::updateProject)
      .flatMap(project -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(project, ProjectDto.class));
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
          .body(projectService.deleteProject(id), ProjectDto.class));
  }

  public Mono<ServerResponse> upload(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
      .map(parts -> parts.toSingleValueMap())
      .map(map -> (FilePart) map.get("file"))
      .flatMap(part -> gridFsTemplate.store(part.content(), part.filename()))
      .flatMap(id -> ServerResponse
        .ok()
        .body(BodyInserters.fromValue(Map.of("id", id.toHexString()))));
  }

  public Mono<ServerResponse> loadImg(ServerRequest request) {
    return readImg(request.pathVariable("id"))
    .flatMap(img -> ServerResponse
      .ok()
      .contentType(MediaType.IMAGE_JPEG)
      .body(img, DefaultDataBuffer.class));
  }

  private Mono<?> readImg(String id) {
    return gridFsTemplate.findOne(query(where("_id").is(id)))
        .flatMap(gridFsTemplate::getResource)
        .map(r -> r.getContent());
  }

}
