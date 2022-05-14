package com.vvs.webfluxadminapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static com.vvs.webfluxadminapp.security.JwtUtil.KEY_ROLE;

import java.util.Collections;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    return Mono.just(authToken)
      .map(token -> jwtUtil.validateToken(token))
      .flatMap(isValid -> jwtUtil.getAllClaimsFromToken(authToken))
      .map(claims -> new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()))));
  }
  
}
