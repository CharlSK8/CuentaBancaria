package com.banco.cuenta_bancaria.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Movimiento;
import com.banco.cuenta_bancaria.mapper.IMovimientosMapper;
import com.banco.cuenta_bancaria.repository.ICuentaBancariaRepository;
import com.banco.cuenta_bancaria.repository.IMovimientoRepository;
import com.banco.cuenta_bancaria.util.Result;


class MovimientoServiceImplTest {

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private IMovimientoRepository movimientoRepository;

    @Mock
    private ICuentaBancariaRepository cuentaBancariaRepository;

    @Mock
    private IMovimientosMapper movimientosMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMostrarMovimientos_CuentaBancariaNoEncontrada() {
        when(cuentaBancariaRepository.findByNumeroCuenta(anyInt())).thenReturn(Optional.empty());

        Result<List<MovimientoResponseDTO>, String> result = movimientoService.mostrarMovimientos(123);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Cuenta bancaria no encontrada", result.getErrors().get(0));
    }

    @Test
    void testMostrarMovimientos_NoSeEncontraronMovimientos() {
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        when(cuentaBancariaRepository.findByNumeroCuenta(anyInt())).thenReturn(Optional.of(cuentaBancaria));
        when(movimientoRepository.findByNumeroCuenta(anyInt())).thenReturn(Optional.empty());

        Result<List<MovimientoResponseDTO>, String> result = movimientoService.mostrarMovimientos(123);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("No se encontraron movimientos", result.getErrors().get(0));
    }

    @Test
    void testMostrarMovimientos_Exito() {
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        Movimiento movimiento = new Movimiento();
        List<Movimiento> movimientos = List.of(movimiento);
        List<MovimientoResponseDTO> movimientoResponseDTOs = List.of(new MovimientoResponseDTO());

        when(cuentaBancariaRepository.findByNumeroCuenta(anyInt())).thenReturn(Optional.of(cuentaBancaria));
        when(movimientoRepository.findByNumeroCuenta(anyInt())).thenReturn(Optional.of(movimientos));
        when(movimientosMapper.toMovimientoResponseDTOList(movimientos)).thenReturn(movimientoResponseDTOs);

        Result<List<MovimientoResponseDTO>, String> result = movimientoService.mostrarMovimientos(123);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(movimientoResponseDTOs, result.getValue());
    }
}