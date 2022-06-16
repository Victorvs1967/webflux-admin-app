package com.vvs.webfluxadminapp.router;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProjectRouter {
  
  @Value("${app.uploads.url}")
  private String uploadsUrl;

  // private final ReactiveGridFsTemplate gridFsTemplate;

  private String uploadPath = "upload/images";
  // private final Path basePath = Paths.get(uploadPath);

  @Bean
  public RouterFunction<ServerResponse> projectRouterFunction(ProjectHandler projectHandler) {
    return RouterFunctions.route()
      .nest(RequestPredicates.path("/api/projects"), builder -> builder
        .GET("", projectHandler::getProjects)
        .POST("", projectHandler::createProject)
        .DELETE("/{id}", projectHandler::deleteProject))
        // .POST("/upload", upload()))
      .build();
  }

  // private HandlerFunction<ServerResponse> upload() {
    
  //   return request -> {
  //     return request
  //       .body(BodyExtractors.toMultipartData())
  //       .flatMap(parts -> {
  //         Map<String, Part> map = parts.toSingleValueMap();
  //         final FilePart filePart = (FilePart) map.get("file");
  //         final File file = new File(uploadsUrl.concat("/").concat(filePart.filename()));
  //         filePart.transferTo(file);
  //         return ServerResponse
  //           .ok()
  //           .body(fromValue("upload"));
  //       });
  //   };
  // }

}
