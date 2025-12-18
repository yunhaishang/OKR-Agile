package com.se.okr_agile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.se.okr_agile.mapper")
public class OkrAgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkrAgileApplication.class, args);
	}

}
