package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SkillRouter {

  @Bean
  public RouterFunction<ServerResponse> skillRouterFunction(SkillHandler skillHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/api/skills"), builder -> builder
        .GET("", skillHandler::getSkills)
        .POST("", skillHandler::createSkill)
        .DELETE("{id}", skillHandler::deleteSkill))
      .build();
  }
  
}
