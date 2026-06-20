package com.luyingdazi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 野趣搭子 - 启动类
 *
 * @author luyingdazi
 */
@SpringBootApplication
@EnableScheduling
public class LuyingdaziApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuyingdaziApplication.class, args);
    }
}
