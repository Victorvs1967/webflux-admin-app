package com.vvs.webfluxadminapp.mapper;

import org.springframework.stereotype.Component;

import com.vvs.webfluxadminapp.dto.SkillDto;
import com.vvs.webfluxadminapp.model.Skill;

@Component
public class SkillMapperImpl implements SkillMapper {

  @Override
  public Skill fromDto(SkillDto skill) {
    return Skill.builder()
      .id(skill.getId())
      .name(skill.getName())
      .description(skill.getDescription())
      .percent(skill.getPercent())
      .build();
  }

  @Override
  public SkillDto toDto(Skill skill) {
    return SkillDto.builder()
      .id(skill.getId())
      .name(skill.getName())
      .description(skill.getDescription())
      .percent(skill.getPercent())
      .build();
  }
  
}
