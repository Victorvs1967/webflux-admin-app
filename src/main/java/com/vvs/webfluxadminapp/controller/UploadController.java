package com.vvs.webfluxadminapp.controller;

import java.util.Map;

import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class UploadController {
  private final ReactiveGridFsTemplate gridFsTemplate;

  @PostMapping("upload")
  public Mono<ResponseEntity<?>> upload(@RequestPart("file") Mono<FilePart> fileParts) {
    return fileParts
      .flatMap(part -> gridFsTemplate.store(part.content(), part.filename()))
      .map(id -> ok().body(Map.of("id", id.toHexString())));
  }
}
