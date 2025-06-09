package com.bryce.metaspace;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.bryce.metaspace.mapper")
public class MetaspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaspaceApplication.class, args);
	}

}
