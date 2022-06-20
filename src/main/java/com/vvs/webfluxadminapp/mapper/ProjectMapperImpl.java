package com.vvs.webfluxadminapp.mapper;

import org.springframework.stereotype.Component;

import com.vvs.webfluxadminapp.dto.ProjectDto;
import com.vvs.webfluxadminapp.model.Project;

@Component
public class ProjectMapperImpl implements ProjectMapper {

  @Override
  public ProjectDto toDto(Project project) {
    return ProjectDto.builder()
      .id(project.getId())
      .name(project.getName())
      .description(project.getDescription())
      .image(project.getImage())
      .imgId(project.getImgId())
      .skills(project.getSkills())
      .links(project.getLinks())
      .build();
  }

  @Override
  public Project fromDto(ProjectDto projectDto) {
    return Project.builder()
      .id(projectDto.getId())
      .name(projectDto.getName())
      .description(projectDto.getDescription())
      .image(projectDto.getImage())
      .imgId(projectDto.getImgId())
      .skills(projectDto.getSkills())
      .links(projectDto.getLinks())
      .build();
  }
  
}
