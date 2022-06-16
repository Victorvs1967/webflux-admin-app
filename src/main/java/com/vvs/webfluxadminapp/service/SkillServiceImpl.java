package com.vvs.webfluxadminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvs.webfluxadminapp.model.Skill;
import com.vvs.webfluxadminapp.repository.SkillRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  private SkillRepository skillRepository;

  @Override
  public Flux<Skill> getSkills() {
    return skillRepository.findAll();
  }

  @Override
  public Mono<Skill> getSkill(String id) {
    return null;
  }

  @Override
  public Mono<Skill> createSkill(Skill skill) {
    return skillRepository.save(skill);
  }

  @Override
  public Mono<Skill> updateSkill(String id) {
    return null;
  }

  @Override
  public Mono<Skill> deleteSkill(String id) {
    return skillRepository.findById(id)
      .switchIfEmpty(Mono.error(RuntimeException::new))
      .flatMap(this::delete)
      .map(skill -> skill);
  }

  private Mono<Skill> delete(Skill skill) {
    return Mono.fromSupplier(() -> {
      skillRepository
        .delete(skill)
        .subscribe();
      return skill;
    });
  }
  
}
