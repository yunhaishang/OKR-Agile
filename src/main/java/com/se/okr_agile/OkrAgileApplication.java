package com.se.okr_agile;

import io.github.cdimascio.dotenv.Dotenv;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.se.okr_agile.mapper")
public class OkrAgileApplication {

	public static void main(String[] args) {
		// 加载.env文件
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		
		// 将环境变量设置到系统属性中
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
		
		SpringApplication.run(OkrAgileApplication.class, args);
	}

}
