package com.basis.sge.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication @ComponentScan(basePackages = "com.basis.sge.service.servico.mapper.EntityMapper")
public class SgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgeApplication.class, args);
	}

}
