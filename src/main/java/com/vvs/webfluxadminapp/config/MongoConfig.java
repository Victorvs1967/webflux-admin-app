package com.vvs.webfluxadminapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;

@Configuration
public class MongoConfig {
  
  @Bean
  public ReactiveGridFsTemplate reactiveGridFsTemplate(ReactiveMongoDatabaseFactory databaseFactory, MappingMongoConverter mongoConverter ) {
    return new ReactiveGridFsTemplate(databaseFactory, mongoConverter);
  }
}
