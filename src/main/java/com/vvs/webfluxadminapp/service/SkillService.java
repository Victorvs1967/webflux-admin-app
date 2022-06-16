package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.model.Skill;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillService {
  Flux<Skill> getSkills();
  Mono<Skill> getSkill(String id);
  Mono<Skill> createSkill(Skill skill);
  Mono<Skill> updateSkill(String id);
  Mono<Skill> deleteSkill(String id);
}
