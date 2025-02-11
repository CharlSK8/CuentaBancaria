package com.banco.cuenta_bancaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banco.cuenta_bancaria.entity.Movimiento;

@Repository
public interface IMovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m JOIN m.cuentaBancaria c WHERE c.numeroCuenta = :numeroCuenta")
    Optional<List<Movimiento>> findByNumeroCuenta(@Param("numeroCuenta") int numeroCuenta);


}