package com.banco.cuenta_bancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Operaciones Bancarias API", version = "1.0.0", description = "API para operaciones bancarias"))
public class CuentaBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentaBancariaApplication.class, args);
	}

}