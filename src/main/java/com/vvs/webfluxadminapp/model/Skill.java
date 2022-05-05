package com.vvs.webfluxadminapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document("skills")
public class Skill {
  
  @Id
  private String id;
  private String name;
  private String description;

}
