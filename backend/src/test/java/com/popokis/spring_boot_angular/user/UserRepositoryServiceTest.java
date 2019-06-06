package com.popokis.spring_boot_angular.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserRepositoryServiceTest {

  private final UserRepositoryService userRepositoryService;
  private final PasswordEncoder passwordEncoder;

  @Test
  void createAndRetrieve() {
    User newUser = User.create("test@test.com", "test", "Test", "Test", User.Role.USER);
    String newUserId = userRepositoryService.create(newUser).block();
    User createdUser = userRepositoryService.read(newUserId).block();

    assertEquals("test@test.com", createdUser.getEmail());
    assertEquals(newUserId, createdUser.getId());
    assertTrue(passwordEncoder.matches("test", createdUser.getPassword()));
  }

  @Test
  void tryToCreateInvalidUser() {
    User admin = User.create("test", "test", "Test", "Test", User.Role.USER);
    assertThrows(ConstraintViolationException.class, () -> userRepositoryService.create(admin));
  }
}