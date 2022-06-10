package com.vvs.webfluxadminapp.repository;

import com.vvs.webfluxadminapp.model.Project;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
}
