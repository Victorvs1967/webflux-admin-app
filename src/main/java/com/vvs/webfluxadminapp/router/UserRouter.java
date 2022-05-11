package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;;

@Configuration
public class UserRouter {
  
  @Bean
  public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
    return route()
      .nest(path("/api"), builder -> builder
        .GET("/users", userHandler::getUsers)
        .GET("/users/{username}", userHandler::getUser)
        .PUT("/users/{username}", userHandler::updateUserData)
        .DELETE("/users/{username}", userHandler::deleteUser))
      .build();
  }
}
