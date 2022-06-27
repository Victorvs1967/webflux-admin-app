package com.vvs.webfluxadminapp.mapper;

import com.vvs.webfluxadminapp.dto.ProjectDto;
import com.vvs.webfluxadminapp.model.Project;

public interface ProjectMapper {
  public ProjectDto toDto(Project project);
  public Project fromDto(ProjectDto projectDto);
}
