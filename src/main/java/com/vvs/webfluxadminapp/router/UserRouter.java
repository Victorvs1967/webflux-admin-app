package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
  
  @Bean
  public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
    return RouterFunctions
      .route(GET("/api/users").and(accept(APPLICATION_JSON)), userHandler::getUsers)
      .andRoute(GET("/api/users/{username}").and(accept(APPLICATION_JSON)), userHandler::getUser)
      .andRoute(PUT("/api/users").and(accept(APPLICATION_JSON)), userHandler::updateUserData)
      .andRoute(DELETE("/api/users/{username}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
  }
}
