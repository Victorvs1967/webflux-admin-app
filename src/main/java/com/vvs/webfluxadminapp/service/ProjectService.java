package com.vvs.webfluxadminapp.service;

import java.util.Map;

import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import com.vvs.webfluxadminapp.dto.ProjectDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
  Flux<ProjectDto> getProjects();
  Mono<ProjectDto> getProject(String id);
  Mono<ProjectDto> createProject(ProjectDto project);
  Mono<ProjectDto> updateProject(ProjectDto project);
  Mono<ProjectDto> deleteProject(String id);
  Mono<Map<String, String>> upload(MultiValueMap<String, Part> file);
  Mono<?> read(String id);
}
