package com.vvs.webfluxadminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vvs.webfluxadminapp.dto.SkillDto;
import com.vvs.webfluxadminapp.mapper.SkillMapper;
import com.vvs.webfluxadminapp.model.Skill;
import com.vvs.webfluxadminapp.repository.SkillRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  private SkillRepository skillRepository;
  @Autowired
  private SkillMapper skillMapper;

  @Override
  public Flux<SkillDto> getSkills() {
    return skillRepository.findAll()
      .map(skillMapper::toDto);
  }

  @Override
  public Mono<SkillDto> getSkill(String id) {
    return skillRepository.findById(id)
      .map(skillMapper::toDto);
  }

  @Override
  public Mono<SkillDto> createSkill(SkillDto skillDto) {
    return skillRepository.save(skillMapper.fromDto(skillDto))
      .map(skillMapper::toDto);
  }

  @Override
  public Mono<SkillDto> updateSkill(SkillDto skillDto) {
    return skillRepository.findById(skillDto.getId())
      .switchIfEmpty(Mono.error(RuntimeException::new))
      .map(skill -> Skill.builder()
        .id(skill.getId())
        .name(skillDto.getName())
        .description(skillDto.getDescription())
        .percent(skillDto.getPercent())
        .build())
      .flatMap(skillRepository::save)
      .map(skillMapper::toDto);
  }

  @Override
  public Mono<SkillDto> deleteSkill(String id) {
    return skillRepository.findById(id)
      .switchIfEmpty(Mono.error(RuntimeException::new))
      .flatMap(this::delete)
      .map(skillMapper::toDto);
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
