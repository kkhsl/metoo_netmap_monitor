package com.metoo.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.metoo.monitor.core.mapper")
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class MetooMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetooMonitorApplication.class, args);
    }

}
