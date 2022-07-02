package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.dto.ProjectDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
  Flux<ProjectDto> getProjects();
  Mono<ProjectDto> getProject(String id);
  Mono<ProjectDto> createProject(ProjectDto project);
  Mono<ProjectDto> updateProject(ProjectDto project);
  Mono<ProjectDto> deleteProject(String id);
}
