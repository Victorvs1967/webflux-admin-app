package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthRouter {
  
  @Bean
  public RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/auth"), builder -> builder
        .POST("/signup", authHandler::signUp)
        .POST("/login", authHandler::login))
      .build();
  }
}
