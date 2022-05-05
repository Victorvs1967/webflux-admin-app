package com.vvs.webfluxadminapp.repository;

import com.vvs.webfluxadminapp.model.Skill;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface SkillRepository extends ReactiveMongoRepository<Skill, String> {
  public Mono<Skill> findSkillByName(String name);
}
