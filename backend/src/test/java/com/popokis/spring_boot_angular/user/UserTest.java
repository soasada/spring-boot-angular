package com.popokis.spring_boot_angular.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserTest {

  private final ObjectMapper mapper;
  private final Validator validator;

  @Test
  void serializeUserWithId() throws JsonProcessingException {
    User admin = User.create("1", "admin@admin.com", "admin", "Administrator", "Administrator", false, User.Role.ADMIN);
    String adminJson = mapper.writeValueAsString(admin);
    assertTrue(adminJson.contains("id"));
  }

  @Test
  void deserializationUserWithId() throws IOException {
    String adminJson = "{\"id\":\"1\",\"email\":\"admin@admin.com\",\"password\":\"admin\",\"name\":\"Administrator\",\"surname\":\"Administrator\",\"active\":false,\"role\":\"ADMIN\"}";
    User admin = mapper.readValue(adminJson, User.class);
    assertEquals("1", admin.getId());
    assertEquals("admin@admin.com", admin.getEmail());
    assertEquals("admin", admin.getPassword());
    assertEquals("Administrator", admin.getName());
    assertEquals("Administrator", admin.getSurname());
    assertFalse(admin.isActive());
    assertEquals(User.Role.ADMIN, admin.getRole());
    assertEquals("ROLE_ADMIN", admin.getAuthority());
  }

  @Test
  void serializeUserWithoutId() throws JsonProcessingException {
    User admin = User.create("admin@admin.com", "admin", "Administrator", "Administrator", User.Role.ADMIN);
    String adminJson = mapper.writeValueAsString(admin);
    assertFalse(adminJson.contains("id"));
  }

  @Test
  void deserializationUserWithoutId() throws IOException {
    String adminJson = "{\"email\":\"admin@admin.com\",\"password\":\"admin\",\"name\":\"Administrator\",\"surname\":\"Administrator\",\"active\":true,\"role\":\"ADMIN\"}";
    User admin = mapper.readValue(adminJson, User.class);
    assertNull(admin.getId());
    assertEquals("admin@admin.com", admin.getEmail());
    assertEquals("admin", admin.getPassword());
    assertEquals("Administrator", admin.getName());
    assertEquals("Administrator", admin.getSurname());
    assertTrue(admin.isActive());
    assertEquals(User.Role.ADMIN, admin.getRole());
    assertEquals("ROLE_ADMIN", admin.getAuthority());
  }

  @Test
  void createValidUserWithId() {
    User admin = User.create("1", "admin@admin.com", "admin", "Administrator", "Administrator", false, User.Role.ADMIN);
    Set<ConstraintViolation<User>> violations = validator.validate(admin);
    assertEquals(0, violations.size());
    assertFalse(admin.isActive());
  }

  @Test
  void createValidUserWithoutId() {
    User admin = User.create("admin@admin.com", "admin", "Administrator", "Administrator", User.Role.ADMIN);
    Set<ConstraintViolation<User>> violations = validator.validate(admin);
    assertEquals(0, violations.size());
    assertTrue(admin.isActive());
  }

  @Test
  void createInvalidUserWithEmail() {
    User admin = User.create("1", "admin@.com", "admin", "Administrator", "Administrator", false, User.Role.ADMIN);
    Set<ConstraintViolation<User>> violations = validator.validate(admin);
    assertEquals(1, violations.size());
    assertEquals("email", violations.iterator().next().getPropertyPath().toString());
  }
}