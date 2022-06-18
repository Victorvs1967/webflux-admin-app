package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.model.Project;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
  Flux<Project> getProjects();
  Mono<Project> getProject(String id);
  Mono<Project> createProject(Project project);
  Mono<Project> updateProject(String id);
  Mono<Project> deleteProject(String id);
  Mono<Object> readImg(String id);
}
