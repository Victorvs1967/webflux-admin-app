package com.vvs.webfluxadminapp.mapper;

import com.vvs.webfluxadminapp.dto.SkillDto;
import com.vvs.webfluxadminapp.model.Skill;

public interface SkillMapper {
  public Skill fromDto(SkillDto skill);
  public SkillDto toDto(Skill skill);
}
