package com.vvs.webfluxadminapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ImageRoute {
  
  @Bean
  public RouterFunction<ServerResponse> imageRouterFunction(ImageHandler imageHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/api/images"), builder -> builder
        .POST("/upload", imageHandler::uploadImg)
        .GET("/download/{id}", imageHandler::downloadImg)
        .GET("", imageHandler::listImg)
        .DELETE("/{id}", imageHandler::deleteImg))
      .build();
}
}
