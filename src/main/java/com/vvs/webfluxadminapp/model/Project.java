package com.vvs.webfluxadminapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document("projects")
public class Project {
  
  @Id
  private String id;
  private String name;
  private Skill[] skills;
  private String image;
  private String description;
}
