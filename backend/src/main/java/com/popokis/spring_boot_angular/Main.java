package com.popokis.spring_boot_angular;

import com.popokis.spring_boot_angular.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfig.class})
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
