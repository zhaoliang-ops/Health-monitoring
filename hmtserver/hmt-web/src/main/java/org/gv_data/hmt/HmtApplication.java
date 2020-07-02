package org.gv_data.hmt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = "org.gv_data.hmt.mapper")
@EnableScheduling
@EnableAsync
public class HmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmtApplication.class, args);
    }

}