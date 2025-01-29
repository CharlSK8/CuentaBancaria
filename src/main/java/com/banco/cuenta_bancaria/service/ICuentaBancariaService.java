package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.response.CuentaBancariaCreadaResponse;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.util.Result;

public interface ICuentaBancariaService {

    Result<SaldoActualResponseDTO, String> mostrarSaldoActual(int numeroCuenta);
    Result<CuentaBancariaCreadaResponse, String> crearCuentaBancaria(CrearCuentaRequestDTO request);
    Result<String, String> retiroCuenta(RetiroCuentaRequestDTO request);
    Result<String, String> depositoCuenta(DepositoCuentaRequestDTO request);
}
