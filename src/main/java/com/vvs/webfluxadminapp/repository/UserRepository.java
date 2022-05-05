package com.vvs.webfluxadminapp.repository;

import com.vvs.webfluxadminapp.model.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
  public Mono<User> findUserById(String id);
  public Mono<User> findUserByUsername(String username);
  public Mono<User> findUserByEmail(String email);
}
