package com.vvs.webfluxadminapp.service;

import java.time.Instant;
import java.util.Date;

import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.error.exception.UserNotFoundException;
import com.vvs.webfluxadminapp.mapper.UserMapper;
import com.vvs.webfluxadminapp.model.User;
import com.vvs.webfluxadminapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;

  @Override
  public Mono<UserDto> getUser(String username) {
    return userRepository.findUserByUsername(username)
        .switchIfEmpty(Mono.error(UserNotFoundException::new))
        .map(userMapper::toDto);
  }

  @Override
  public Flux<UserDto> getUsers() {
    return userRepository.findAll()
        .map(userMapper::toDto);
  }

  @Override
  public Mono<UserDto> updateUserData(UserDto userDto) {
    return userRepository.findUserByUsername(userDto.getUsername())
      .switchIfEmpty(Mono.error(UserNotFoundException::new))
      .map(user -> User.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .email(user.getEmail())
        .firstName(userDto.getFirstName())
        .lastName(userDto.getLastName())
        .phone(userDto.getPhone())
        .address(userDto.getAddress())
        .onCreate(user.getOnCreate())
        .onUpdate(Date.from(Instant.now()))
        .isActive(user.isActive())
        .role(user.getRole())
        .build())
      .flatMap(userRepository::save)
      .map(userMapper::toDto);
  }

  @Override
  public Mono<UserDto> deleteUser(String username) {
    return userRepository.findUserByUsername(username)
    .switchIfEmpty(Mono.error(UserNotFoundException::new))
      .flatMap(this::delete)
      .map(userMapper::toDto);
  }

  private Mono<User> delete(User user) {
    return Mono.fromSupplier(() -> {
      userRepository
          .delete(user)
          .subscribe();
      return user;
    });
  }

}
