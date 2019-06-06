package com.popokis.spring_boot_angular.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
  Mono<User> findByEmail(String email);
}
