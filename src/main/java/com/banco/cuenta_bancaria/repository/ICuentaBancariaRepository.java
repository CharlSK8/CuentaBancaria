package com.banco.cuenta_bancaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.cuenta_bancaria.entity.CuentaBancaria;

@Repository
public interface ICuentaBancariaRepository extends JpaRepository<CuentaBancaria, Long>{

    Optional<CuentaBancaria> findByNumeroCuenta(int numeroCuenta);

}
