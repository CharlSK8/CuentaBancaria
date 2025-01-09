package com.banco.cuenta_bancaria.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Movimiento;
import com.banco.cuenta_bancaria.mapper.IMovimientosMapper;
import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;
import com.banco.cuenta_bancaria.repository.IMovimientoRepository;
import com.banco.cuenta_bancaria.service.IMovimientoService;
import com.banco.cuenta_bancaria.util.Result;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements IMovimientoService {

    private final IMovimientoRepository movimientoRepository;
    private final ICuentaBancariaRepository cuentaBancariaRepository;
    private final IMovimientosMapper movimientosMapper;

    @Override
    @Transactional
    public Result<List<MovimientoResponseDTO>, String> mostrarMovimientos(int numeroCuenta) {

        Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta);
        if(!cuentaBancaria.isPresent()) {
            return Result.failure(List.of("Cuenta bancaria no encontrada"), HttpStatus.BAD_REQUEST);
        }

        Optional<List<Movimiento>> movimientos = movimientoRepository.findByNumeroCuenta(numeroCuenta);

        if(!movimientos.isPresent()) {
            return Result.failure(List.of("No se encontraron movimientos"), HttpStatus.BAD_REQUEST);
        }

        return Result.success(movimientosMapper.toMovimientoResponseDTOList(movimientos.get()));
    }

}
