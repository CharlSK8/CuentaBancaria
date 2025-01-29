package com.banco.cuenta_bancaria.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CuentaBancariaUtil {

    private final ICuentaBancariaRepository cuentaBancariaRepository;
    private Random random = new Random(); 

    public int generarNumeroCuenta() {
        int numeroCuenta;
        do {
            numeroCuenta = 1000000000 + random.nextInt(900000000);
        } while (existeNumeroCuenta(numeroCuenta));
        return numeroCuenta;
    }

    private boolean existeNumeroCuenta(int numeroCuenta) {
        return cuentaBancariaRepository.existsByNumeroCuenta(numeroCuenta);
    }

}
