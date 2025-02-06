package com.banco.cuenta_bancaria.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.response.CuentaBancariaCreadaResponse;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.mapper.ICuentaBancariaMapper;
import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;
import com.banco.cuenta_bancaria.repository.IMovimientoRepository;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;
import com.banco.cuenta_bancaria.util.CuentaBancariaUtil;
import com.banco.cuenta_bancaria.util.Result;



class CuentaBancariaServiceImplTest {

    @Mock
    private ICuentaBancariaRepository cuentaBancariaRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IMovimientoRepository movimientoRepository;

    @Mock
    private ICuentaBancariaMapper cuentaBancariaMapper;

    @Mock
    private CuentaBancariaUtil cuentaBancariaUtil;

    @InjectMocks
    private CuentaBancariaServiceImpl cuentaBancariaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCuentaBancaria() {
        CrearCuentaRequestDTO request = new CrearCuentaRequestDTO();
        request.setNumeroIdetificacion("12345");

        Usuario usuario = new Usuario();
        usuario.setNumeroIdetificacion("12345");

        when(usuarioRepository.findByNumeroIdetificacionAndActivoTrue("12345")).thenReturn(Optional.of(usuario));
        when(cuentaBancariaMapper.toEntity(any(), any(), any())).thenReturn(new CuentaBancaria());
        when(cuentaBancariaRepository.save(any())).thenReturn(new CuentaBancaria());

        Result<CuentaBancariaCreadaResponse, String> result = cuentaBancariaService.crearCuentaBancaria(request);

        assertTrue(result.isSuccess());
    }

    @Test
    void crearCuentaBancaria_usuarioNoEncontrado() {
        String numeroIdetificacion = "12345";
        CrearCuentaRequestDTO request = new CrearCuentaRequestDTO();
        request.setNumeroIdetificacion("12345");

        when(usuarioRepository.findByNumeroIdetificacionAndActivoTrue(numeroIdetificacion)).thenReturn(Optional.empty());

        Result<CuentaBancariaCreadaResponse, String> resultado = cuentaBancariaService.crearCuentaBancaria(request);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Usuario no encontrado", resultado.getErrors().get(0));
    }

    @Test
    void testMostrarSaldoActual() {
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setSaldo(BigDecimal.valueOf(1000));

        when(cuentaBancariaRepository.findByNumeroCuenta(1)).thenReturn(Optional.of(cuentaBancaria));

        Result<SaldoActualResponseDTO, String> result = cuentaBancariaService.mostrarSaldoActual(1);

        assertTrue(result.isSuccess());
        assertEquals(BigDecimal.valueOf(1000), result.getValue().getSaldo());
    }

    @Test
    void mostrarSaldoActual_cuentaNoEncontrada() {
        int numeroCuenta = 12345;
        when(cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.empty());

        Result<SaldoActualResponseDTO, String> resultado = cuentaBancariaService.mostrarSaldoActual(numeroCuenta);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(List.of("Cuenta bancaria no encontrada"), resultado.getErrors());
    }

    @Test
    void testRetiroCuenta() {
        RetiroCuentaRequestDTO request = new RetiroCuentaRequestDTO();
        request.setNumeroCuenta(1);
        request.setMonto(BigDecimal.valueOf(500));

        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setSaldo(BigDecimal.valueOf(1000));

        when(cuentaBancariaRepository.findByNumeroCuenta(1)).thenReturn(Optional.of(cuentaBancaria));

        Result<String, String> result = cuentaBancariaService.retiroCuenta(request);

        assertTrue(result.isSuccess());
        assertEquals("Retiro de cuenta realizado correctamente", result.getValue());
    }

    @Test
    void retiroCuenta_cuentaNoEncontrada() {
        int numeroCuenta = 12345;
        RetiroCuentaRequestDTO request = new RetiroCuentaRequestDTO();
        request.setNumeroCuenta(1);
        request.setMonto(BigDecimal.valueOf(500));
        when(cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.empty());

        Result<String, String> resultado = cuentaBancariaService.retiroCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(List.of("Cuenta bancaria no encontrada"), resultado.getErrors());
    }

    @Test
    void retiroCuenta_saldoInsuficiente() {
        RetiroCuentaRequestDTO request = new RetiroCuentaRequestDTO();
        request.setMonto(new BigDecimal("1000")); 

        int numeroCuenta = 12345;
        request.setNumeroCuenta(numeroCuenta);

        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setSaldo(new BigDecimal("500"));
        Optional<CuentaBancaria> cuentaOptional = Optional.of(cuentaBancaria);

        when(cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuentaOptional);

        Result<String, String> resultado = cuentaBancariaService.retiroCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(List.of("Saldo insuficiente"), resultado.getErrors());
    }

    @Test
    void testDepositoCuenta() {
        DepositoCuentaRequestDTO request = new DepositoCuentaRequestDTO();
        request.setNumeroCuenta(1);
        request.setMonto(BigDecimal.valueOf(500));

        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setSaldo(BigDecimal.valueOf(1000));

        when(cuentaBancariaRepository.findByNumeroCuenta(1)).thenReturn(Optional.of(cuentaBancaria));

        Result<String, String> result = cuentaBancariaService.depositoCuenta(request);

        assertTrue(result.isSuccess());
        assertEquals("Deposito de cuenta realizado correctamente", result.getValue());
    }

    @Test
    void depositoCuenta_saldoInsuficiente() {
        DepositoCuentaRequestDTO request = new DepositoCuentaRequestDTO();
        request.setMonto(new BigDecimal("0")); 

        int numeroCuenta = 12345;
        request.setNumeroCuenta(numeroCuenta);

        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setSaldo(new BigDecimal("500"));
        Optional<CuentaBancaria> cuentaOptional = Optional.of(cuentaBancaria);

        when(cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(cuentaOptional);

        Result<String, String> resultado = cuentaBancariaService.depositoCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(List.of("El monto debe ser mayor a 0"), resultado.getErrors());
    }

    @Test
    void depositoCuenta_cuentaNoEncontrada() {
        int numeroCuenta = 12345;
        DepositoCuentaRequestDTO request = new DepositoCuentaRequestDTO();
        request.setMonto(new BigDecimal("1000")); 

        when(cuentaBancariaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.empty());

        Result<String, String> resultado = cuentaBancariaService.depositoCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(List.of("Cuenta bancaria no encontrada"), resultado.getErrors());
    }
}