package com.vvs.webfluxadminapp.repository;

import com.vvs.webfluxadminapp.model.Project;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
}
