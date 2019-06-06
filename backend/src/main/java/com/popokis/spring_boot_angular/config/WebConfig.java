package com.popokis.spring_boot_angular.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.popokis.spring_boot_angular.user.UserRepository;
import com.popokis.spring_boot_angular.user.UserRepositoryService;
import com.popokis.spring_boot_angular.user.UserRepositoryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
public class WebConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
      @Override
      public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
        if (ac.hasAnnotation(JsonPOJOBuilder.class)) { //If no annotation present use default as empty prefix
          return super.findPOJOBuilderConfig(ac);
        }
        return new JsonPOJOBuilder.Value("build", "");
      }
    });
    return mapper;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Validator localValidatorFactoryBean() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public UserRepositoryService userRepositoryService(UserRepository repository, PasswordEncoder passwordEncoder) {
    return new UserRepositoryServiceImpl(repository, passwordEncoder);
  }
}
