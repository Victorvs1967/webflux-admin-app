package com.vvs.webfluxadminapp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("posts")
public class Post {
  
  @Id
  private String id;
  private User author;

  private String title;
  private String description;
  private Project[] projects;
  private Skill[] skills;

  private Date onCreate;
  private Date onUpdate;
  private Date onPublicate;
  
}
