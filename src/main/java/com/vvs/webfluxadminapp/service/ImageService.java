package com.vvs.webfluxadminapp.service;

import java.util.Map;

import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {
  Mono<Map<String, String>> upload(MultiValueMap<String, Part> file);
  Mono<?> download(String id);
  Mono<Void> delete(String id);
  Flux<Map<String, String>> listFiles();
}
