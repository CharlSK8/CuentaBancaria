package com.banco.cuenta_bancaria.controller;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banco.cuenta_bancaria.service.ICuentaBancariaService;
import com.banco.cuenta_bancaria.dto.request.*;
import com.banco.cuenta_bancaria.dto.response.*;
import com.banco.cuenta_bancaria.util.Result;

class CuentaBancariaControllerTest {

    @Mock
    private ICuentaBancariaService cuentaBancariaService;

    @InjectMocks
    private CuentaBancariaController cuentaBancariaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCuenta_CuandoEsExitoso_RetornaResponseEntityOK() {
        CrearCuentaRequestDTO request = new CrearCuentaRequestDTO();
        Result<String, String> successResult = Result.success("Cuenta creada exitosamente");
        when(cuentaBancariaService.crearCuentaBancaria(request)).thenReturn(successResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.crearCuenta(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cuenta creada exitosamente", response.getBody().getMessage());
    }

    @Test
    void crearCuenta_CuandoHayError_RetornaResponseEntityBadRequest() {
        CrearCuentaRequestDTO request = new CrearCuentaRequestDTO();
        Result<String, String> errorResult = Result.failure(List.of("Error al crear cuenta"), HttpStatus.BAD_REQUEST);
        when(cuentaBancariaService.crearCuentaBancaria(request)).thenReturn(errorResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.crearCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al crear cuenta", response.getBody().getMessage());
    }

    @Test
    void mostrarSaldo_CuandoEsExitoso_RetornaResponseEntityOK() {
        int numeroCuenta = 123456;
        SaldoActualResponseDTO saldoResponse = new SaldoActualResponseDTO();
        Result<SaldoActualResponseDTO, String> successResult = Result.success(saldoResponse);
        when(cuentaBancariaService.mostrarSaldoActual(numeroCuenta)).thenReturn(successResult);

        ResponseEntity<ResponseDTO> response = cuentaBancariaController.mostrarSaldo(numeroCuenta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se ha mostrado el saldo correctamente", response.getBody().getMessage());
    }

    @Test
    void retiroCuenta_CuandoEsExitoso_RetornaResponseEntityOK() {
        RetiroCuentaRequestDTO request = new RetiroCuentaRequestDTO();
        Result<String, String> successResult = Result.success("Retiro realizado exitosamente");
        when(cuentaBancariaService.retiroCuenta(request)).thenReturn(successResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.retiroCuenta(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Retiro realizado exitosamente", response.getBody().getMessage());
    }

    @Test
    void depositoCuenta_CuandoEsExitoso_RetornaResponseEntityOK() {
        DepositoCuentaRequestDTO request = new DepositoCuentaRequestDTO();
        Result<String, String> successResult = Result.success("Depósito realizado exitosamente");
        when(cuentaBancariaService.depositoCuenta(request)).thenReturn(successResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.depositoCuenta(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Depósito realizado exitosamente", response.getBody().getMessage());
    }
}
