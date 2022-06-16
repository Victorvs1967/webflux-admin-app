package com.vvs.webfluxadminapp.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class UploadController {

  private final ReactiveGridFsTemplate gridFsTemplate;

  // private String uploadPath = "upload/images";
  // private final Path basePath = Paths.get(uploadPath);

  @PostMapping("upload")
  public Mono<ResponseEntity<?>> upload(@RequestPart("file") Mono<FilePart> filePart) {
    return filePart
      .doOnNext(fp -> System.out.println("Recived file: " + fp.filename()))
      .flatMap(fp -> gridFsTemplate.store(fp.content(), fp.filename()))
      .map(id -> ok()
      .body(Map.of("id", id.toHexString())));
      // .flatMap(fp -> fp.transferTo(basePath.resolve(fp.filename())))
      // .then();
  }

  @GetMapping("read/{id}")
  public Flux<Void> read(@PathVariable String id, ServerWebExchange exchange) {
    return gridFsTemplate.findOne(query(where("_id").is(id))).log()
      .flatMap(gridFsTemplate::getResource)
      .flatMapMany(r -> exchange.getResponse()
        .writeWith(r.getDownloadStream()));
  }

}
