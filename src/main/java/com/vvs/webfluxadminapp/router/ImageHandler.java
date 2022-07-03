package com.vvs.webfluxadminapp.router;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.webfluxadminapp.service.ImageService;

import reactor.core.publisher.Mono;

@Component
public class ImageHandler {
  
  @Autowired
  private ImageService imageService;

  public Mono<ServerResponse> uploadImg(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
        .map(imageService::upload)
        .flatMap(id -> ServerResponse
          .ok()
          .body(id, Map.class));
  }

  public Mono<ServerResponse> downloadImg(ServerRequest request) {
    return imageService.download(request.pathVariable("id"))
        .flatMap(img -> ServerResponse
          .ok()
          .contentType(MediaType.IMAGE_JPEG)
          .body(img, DefaultDataBuffer.class));
  }

  public Mono<ServerResponse> listImg(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(imageService.listFiles(), ArrayList.class);
  }

  public Mono<ServerResponse> deleteImg(ServerRequest request) {
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(imageService.delete(request.pathVariable("id")), Void.class);
  }

}
