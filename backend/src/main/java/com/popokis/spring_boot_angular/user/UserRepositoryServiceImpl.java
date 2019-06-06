package com.popokis.spring_boot_angular.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Mono<String> create(@Valid User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return repository.save(user).map(User::getId);
  }

  @Override
  public Mono<User> read(String id) {
    return repository.findById(id);
  }

  @Override
  public Mono<Integer> update(@Valid User user) {
    return null;
  }

  @Override
  public Mono<Integer> delete(String id) {
    return null;
  }

  @Override
  public Mono<User> findByEmail(String email) {
    return null;
  }

  @Override
  public Flux<User> all() {
    return null;
  }
}
