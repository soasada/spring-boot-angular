package com.popokis.spring_boot_angular.user;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public interface UserRepositoryService {
  Mono<String> create(@Valid User user);

  Mono<User> read(String id);

  Mono<Integer> update(@Valid User user);

  Mono<Integer> delete(String id);

  Mono<User> findByEmail(String email);

  Flux<User> all();
}
