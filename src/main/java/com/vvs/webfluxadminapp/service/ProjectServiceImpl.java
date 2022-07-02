package com.vvs.webfluxadminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvs.webfluxadminapp.dto.ProjectDto;
import com.vvs.webfluxadminapp.error.exception.ProjectNotFoundException;
import com.vvs.webfluxadminapp.mapper.ProjectMapper;
import com.vvs.webfluxadminapp.model.Project;
import com.vvs.webfluxadminapp.repository.ProjectRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private ProjectMapper projectMapper;

  @Override
  public Flux<ProjectDto> getProjects() {
    return projectRepository.findAll()
      .map(projectMapper::toDto);
  }

  @Override
  public Mono<ProjectDto> getProject(String id) {
    return projectRepository.findById(id)
      .switchIfEmpty(Mono.empty())
      .map(projectMapper::toDto);
  }

  @Override
  public Mono<ProjectDto> createProject(ProjectDto projectDto) {
    return Mono.just(projectDto)
      .map(projectMapper::fromDto)
      .flatMap(projectRepository::save)
      .map(projectMapper::toDto);
  }

  @Override
  public Mono<ProjectDto> updateProject(ProjectDto projectDto) {
    return projectRepository.findById(projectDto.getId())
      .switchIfEmpty(Mono.error(ProjectNotFoundException::new))
      .map(project -> Project.builder()
        .id(project.getId())
        .name(projectDto.getName())
        .description(projectDto.getDescription())
        .image(projectDto.getImage())
        .skills(projectDto.getSkills())
        .links(projectDto.getLinks())
        .build())
      .flatMap(projectRepository::save)
      .map(projectMapper::toDto);
  }

  @Override
  public Mono<ProjectDto> deleteProject(String id) {
    return projectRepository.findById(id)
      .switchIfEmpty(Mono.error(ProjectNotFoundException::new))
      .flatMap(this::delete)
      .map(projectMapper::toDto);
  }

  private Mono<Project> delete(Project project) {
    return Mono.fromSupplier(() -> {
      projectRepository
        .delete(project)
        .subscribe();
      return project;
    });
  }
}
