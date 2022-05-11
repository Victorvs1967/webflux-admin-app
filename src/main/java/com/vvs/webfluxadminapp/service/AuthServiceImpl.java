package com.vvs.webfluxadminapp.service;

import com.vvs.webfluxadminapp.dto.ResponseDto;
import com.vvs.webfluxadminapp.dto.UserDto;
import com.vvs.webfluxadminapp.error.exception.EmailAlreadyExistException;
import com.vvs.webfluxadminapp.error.exception.UserAlreadyExistException;
import com.vvs.webfluxadminapp.error.exception.UserNotFoundException;
import com.vvs.webfluxadminapp.error.exception.WrongCredentialException;
import com.vvs.webfluxadminapp.mapper.UserMapper;
import com.vvs.webfluxadminapp.repository.UserRepository;
import com.vvs.webfluxadminapp.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UserMapper userMapper;

  @Override
  public Mono<UserDto> signUp(UserDto userDto) {
    return isUserExist(userDto.getUsername())
      .filter(userExist -> !userExist)
      .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
      .doOnNext(_Boolean -> isEmailExist(userDto.getEmail()))
      .filter(emailExist -> !emailExist)
      .switchIfEmpty(Mono.error(EmailAlreadyExistException::new))
      .map(aBoolean -> userDto)
      .map(userMapper::fromDto)
      .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
      .flatMap(userRepository::save)
      .map(userMapper::toDto);
}

  @Override
  public Mono<ResponseDto> login(String username, String password) {
    return userRepository.findUserByUsername(username)
      .switchIfEmpty(Mono.error(UserNotFoundException::new))
      .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
      .map(userDetails -> jwtUtil.generateToken(userDetails))
      .map(token -> ResponseDto.builder()
        .token(token)
        .build())
      .switchIfEmpty(Mono.error(WrongCredentialException::new));
  }

  private Mono<Boolean> isUserExist(String username) {
    return userRepository.findUserByUsername(username)
      .map(user -> true)
      .switchIfEmpty(Mono.just(false));
  }

  private Mono<Boolean> isEmailExist(String email) {
    return userRepository.findUserByEmail(email)
      .map(user -> true)
      .switchIfEmpty(Mono.just(false));
  }
  
}
