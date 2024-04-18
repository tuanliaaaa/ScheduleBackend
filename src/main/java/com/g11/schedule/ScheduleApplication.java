package com.g11.schedule;

import com.g11.schedule.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class ScheduleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScheduleApplication.class, args);
  }

}
