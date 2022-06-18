package com.vvs.webfluxadminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.stereotype.Service;

import com.vvs.webfluxadminapp.model.Project;
import com.vvs.webfluxadminapp.repository.ProjectRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private ReactiveGridFsTemplate gridFsTemplate;

  @Override
  public Flux<Project> getProjects() {
    return projectRepository.findAll();
  }

  @Override
  public Mono<Project> getProject(String id) {
    return projectRepository.findById(id)
      .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Project> createProject(Project project) {
    return projectRepository.save(project);
  }

  @Override
  public Mono<Project> updateProject(String id) {
    return null;
  }

  @Override
  public Mono<Project> deleteProject(String id) {
    return projectRepository.findById(id)
      .switchIfEmpty(Mono.error(RuntimeException::new))
      .flatMap(this::delete)
      .map(project -> project);
  }

  // private Mono<Boolean> isProjectExist(String id) {
  //   return projectRepository.findById(id)
  //     .map(user -> true)
  //     .switchIfEmpty(Mono.just(false));
  // }
  
  private Mono<Project> delete(Project project) {
    return Mono.fromSupplier(() -> {
      projectRepository
        .delete(project)
        .subscribe();
      return project;
    });
  }

  @Override
  public Mono<Object> readImg(String id) {
    return gridFsTemplate.findOne(query(where("_id").is(id)))
      .flatMap(gridFsTemplate::getResource)
      .map(r -> r.getContent());
  }

}
