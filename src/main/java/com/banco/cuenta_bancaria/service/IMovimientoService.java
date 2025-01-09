package com.banco.cuenta_bancaria.service;

import java.util.List;

import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.util.Result;

public interface IMovimientoService {

    Result<List<MovimientoResponseDTO>, String> mostrarMovimientos(int numeroCuenta);

}
