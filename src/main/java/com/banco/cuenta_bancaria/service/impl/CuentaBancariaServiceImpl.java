package com.banco.cuenta_bancaria.service.impl;

import com.banco.cuenta_bancaria.service.ICuentaBancariaService;
import com.banco.cuenta_bancaria.util.Result;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Movimiento;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.enums.TipoMovimiento;
import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;
import com.banco.cuenta_bancaria.repository.IMovimientoRepository;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;

@Service
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements ICuentaBancariaService {

    private final ICuentaBancariaRepository cuentaBancariaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IMovimientoRepository movimientoRepository;

    @Override
    public Result<String, String> crearCuentaBancaria(CrearCuentaRequestDTO request) {

        Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(request.getNumeroCuenta());
        if(cuentaBancaria.isPresent()) {
            return Result.failure(List.of("Cuenta bancaria ya existe"), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuario = usuarioRepository.findByNumeroIdetificacionAndActivoTrue(request.getNumeroIdetificacion());
        if(!usuario.isPresent()) {
            return Result.failure(List.of("Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }

        CuentaBancaria nuevaCuentaBancaria = CuentaBancaria.builder()   
                .numeroCuenta(request.getNumeroCuenta())
                .saldo(request.getSaldo())
                .tipoCuenta(request.getTipoCuenta())
                .usuario(usuario.get())
                .build();

        cuentaBancariaRepository.save(nuevaCuentaBancaria);
        return Result.success("Cuenta bancaria creada correctamente");
    }

    @Override
    public Result<SaldoActualResponseDTO, String> mostrarSaldoActual(int numeroCuenta) {
        Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta);
        if(!cuentaBancaria.isPresent()) {
            return Result.failure(List.of("Cuenta bancaria no encontrada"), HttpStatus.BAD_REQUEST);
        }
        
        return Result.success(SaldoActualResponseDTO.builder().saldo(cuentaBancaria.get().getSaldo()).build());
    }
}
