package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.util.Result;

public interface ICuentaBancariaService {

    Result<SaldoActualResponseDTO, String> mostrarSaldoActual(int numeroCuenta);
    Result<String, String> crearCuentaBancaria(CrearCuentaRequestDTO request);
}
