package com.banco.cuenta_bancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CuentaBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentaBancariaApplication.class, args);
	}

}
	