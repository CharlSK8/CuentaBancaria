package com.banco.cuenta_bancaria.service.impl;

import com.banco.cuenta_bancaria.dto.response.CuentaBancariaResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancariaEventMessage;
import com.banco.cuenta_bancaria.service.ICuentaBancariaService;
import com.banco.cuenta_bancaria.util.CuentaBancariaUtil;
import com.banco.cuenta_bancaria.util.Result;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.response.CuentaBancariaCreadaResponse;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Movimiento;
import com.banco.cuenta_bancaria.enums.TipoMovimiento;
import com.banco.cuenta_bancaria.mapper.ICuentaBancariaMapper;
import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;
import com.banco.cuenta_bancaria.repository.IMovimientoRepository;

@Service
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements ICuentaBancariaService {

    private final ICuentaBancariaRepository cuentaBancariaRepository;
    private final IMovimientoRepository movimientoRepository;
    private final ICuentaBancariaMapper cuentaBancariaMapper;
    private final CuentaBancariaUtil cuentaBancariaUtil;
    private final JmsMessageService jmsMessageService;

    @Override
    public Result<CuentaBancariaCreadaResponse, String> crearCuentaBancaria(CrearCuentaRequestDTO request) {


//        Optional<Usuario> usuario = usuarioRepository.findBynumeroIdentificacionAndActivoTrue(request.getnumeroIdentificacion());
//        if(!usuario.isPresent()) {
//            return Result.failure(List.of("Usuario no encontrado"), HttpStatus.BAD_REQUEST);
//        }

        /*CuentaBancaria cuentaGuardada = cuentaBancariaRepository.save(cuentaBancariaMapper.toEntity(request, cuentaBancariaUtil));
        return Result.success(CuentaBancariaCreadaResponse.builder().numeroCuenta(cuentaGuardada.getNumeroCuenta()).build());*/

        CuentaBancaria cuentaGuardada = cuentaBancariaRepository.save(cuentaBancariaMapper.toEntity(request, cuentaBancariaUtil));

        // ActiveMQ
        CuentaBancariaEventMessage eventMessage = new CuentaBancariaEventMessage("REGISTER",
                cuentaGuardada.getNumeroCuenta(),
                cuentaGuardada.getNumeroIdentificacion(),
                cuentaGuardada.getSaldo(),
                cuentaGuardada.getTipoCuenta(),
                cuentaGuardada.isActiva());
        jmsMessageService.sendEvent("CuentaBancaria", eventMessage); //Enviamos

        return Result.success(new CuentaBancariaCreadaResponse(cuentaGuardada.getNumeroCuenta()));
    }

    @Override
    public List<CuentaBancariaResponseDTO> obtenerCuentasPorIdentificacion(Long identificacion) {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findByNumeroIdentificacion(identificacion);
        return cuentaBancariaMapper.toListDTO(cuentasBancarias);
    }

    @Override
    public Result<SaldoActualResponseDTO, String> mostrarSaldoActual(int numeroCuenta) {
        Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta);
        if(!cuentaBancaria.isPresent()) {
            return Result.failure(List.of("Cuenta bancaria no encontrada"), HttpStatus.BAD_REQUEST);
        }
        
        return Result.success(SaldoActualResponseDTO.builder().saldo(cuentaBancaria.get().getSaldo()).build());
    }

	@Override
	public Result<String, String> retiroCuenta(RetiroCuentaRequestDTO request) {
		Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(request.getNumeroCuenta());
		if(!cuentaBancaria.isPresent()) {
			return Result.failure(List.of("Cuenta bancaria no encontrada"), HttpStatus.BAD_REQUEST);
		}

        if(request.getMonto().compareTo(cuentaBancaria.get().getSaldo()) > 0) {
            return Result.failure(List.of("Saldo insuficiente"), HttpStatus.BAD_REQUEST);
        }
        Movimiento movimiento = Movimiento.builder()
                .tipoMovimiento(TipoMovimiento.RETIRO.toString())
                .monto(request.getMonto())
                .cuentaBancaria(cuentaBancaria.get())
                .fechaMovimiento(LocalDateTime.now())
                .build();

        cuentaBancaria.get().setSaldo(cuentaBancaria.get().getSaldo().subtract(request.getMonto()));
        cuentaBancariaRepository.save(cuentaBancaria.get());
        movimientoRepository.save(movimiento);
        return Result.success("Retiro de cuenta realizado correctamente");
	}

    @Override
    public Result<String, String> depositoCuenta(DepositoCuentaRequestDTO request) {
        if(request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            return Result.failure(List.of("El monto debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        }

        Optional<CuentaBancaria> cuentaBancaria = cuentaBancariaRepository.findByNumeroCuenta(request.getNumeroCuenta());
        if(!cuentaBancaria.isPresent()) {
            return Result.failure(List.of("Cuenta bancaria no encontrada"), HttpStatus.BAD_REQUEST);
        }

        Movimiento movimiento = Movimiento.builder()
                .tipoMovimiento(TipoMovimiento.DEPOSITO.toString())
                .monto(request.getMonto())
                .cuentaBancaria(cuentaBancaria.get())
                .fechaMovimiento(LocalDateTime.now())
                .build();

        cuentaBancaria.get().setSaldo(cuentaBancaria.get().getSaldo().add(request.getMonto()));
        cuentaBancariaRepository.save(cuentaBancaria.get());
        movimientoRepository.save(movimiento);
        return Result.success("Deposito de cuenta realizado correctamente");
    }

}
