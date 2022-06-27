package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.dto.SkillDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillService {
  Flux<SkillDto> getSkills();
  Mono<SkillDto> getSkill(String id);
  Mono<SkillDto> createSkill(SkillDto skill);
  Mono<SkillDto> updateSkill(SkillDto skill);
  Mono<SkillDto> deleteSkill(String id);
}
