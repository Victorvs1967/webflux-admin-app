package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProjectRouter {
  
  @Bean
  public RouterFunction<ServerResponse> projectRouterFunction(ProjectHandler projectHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/api/projects"), builder -> builder
        .GET("", projectHandler::getProjects)
        .POST("", projectHandler::createProject)
        .DELETE("/{id}", projectHandler::deleteProject))
      .build();
  }
}
