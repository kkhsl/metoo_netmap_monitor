package com.metoo.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.metoo.monitor.core.mapper")
@SpringBootApplication
public class MetooMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetooMonitorApplication.class, args);
    }

}
