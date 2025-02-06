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
        CuentaBancariaCreadaResponse cuentaCreada = new CuentaBancariaCreadaResponse(); // Crea un objeto de respuesta
        Result<CuentaBancariaCreadaResponse, String> successResult = Result.success(cuentaCreada);
        when(cuentaBancariaService.crearCuentaBancaria(request)).thenReturn(successResult);

        ResponseEntity<ResponseDTO<?>> response = cuentaBancariaController.crearCuenta(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se ha creado la cuenta satisfactoriamente.", response.getBody().getMessage());
        assertEquals(cuentaCreada, ((ResponseDTO<CuentaBancariaCreadaResponse>) response.getBody()).getResponse()); 
    }

    @Test
    void crearCuenta_CuandoHayError_RetornaResponseEntityBadRequest() {
        CrearCuentaRequestDTO request = new CrearCuentaRequestDTO();
        List<String> errors = List.of("Error 1", "Error 2"); 
        Result<CuentaBancariaCreadaResponse, String> errorResult = Result.failure(errors, HttpStatus.BAD_REQUEST);
        when(cuentaBancariaService.crearCuentaBancaria(request)).thenReturn(errorResult);

        ResponseEntity<ResponseDTO<?>> response = cuentaBancariaController.crearCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(String.join("\n", errors), response.getBody().getMessage()); 
    }

    @Test
    void mostrarSaldo_CuandoEsExitoso_RetornaResponseEntityOK() {
        int numeroCuenta = 123456;
        SaldoActualResponseDTO saldoResponse = new SaldoActualResponseDTO();
        Result<SaldoActualResponseDTO, String> successResult = Result.success(saldoResponse);
        when(cuentaBancariaService.mostrarSaldoActual(numeroCuenta)).thenReturn(successResult);

        ResponseEntity<ResponseDTO<?>> response = cuentaBancariaController.mostrarSaldo(numeroCuenta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se ha mostrado el saldo correctamente", response.getBody().getMessage());
    }

    @Test
    void mostrarSaldo_CuandoHayError_RetornaResponseEntityBadRequest() {
        int numeroCuenta = 123456;
        List<String> errors = List.of("Error 1", "Error 2"); 
        Result<SaldoActualResponseDTO, String> errorResult = Result.failure(errors, HttpStatus.BAD_REQUEST);
        when(cuentaBancariaService.mostrarSaldoActual(numeroCuenta)).thenReturn(errorResult);

        ResponseEntity<ResponseDTO<?>> response = cuentaBancariaController.mostrarSaldo(numeroCuenta);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(String.join("\n", errors), response.getBody().getMessage()); 
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
    void retiroCuenta_CuandoHayError_RetornaResponseEntityBadRequest() {
        RetiroCuentaRequestDTO request = new RetiroCuentaRequestDTO();
        List<String> errors = List.of("Error 1", "Error 2"); 
        Result<String, String> errorResult = Result.failure(errors, HttpStatus.BAD_REQUEST);
        when(cuentaBancariaService.retiroCuenta(request)).thenReturn(errorResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.retiroCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(String.join("\n", errors), response.getBody().getMessage()); 
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

    @Test
    void depositoCuenta_CuandoHayError_RetornaResponseEntityBadRequest() {
        DepositoCuentaRequestDTO request = new DepositoCuentaRequestDTO();
        List<String> errors = List.of("Error 1", "Error 2"); 
        Result<String, String> errorResult = Result.failure(errors, HttpStatus.BAD_REQUEST);
        when(cuentaBancariaService.depositoCuenta(request)).thenReturn(errorResult);

        ResponseEntity<ResponseDTO<String>> response = cuentaBancariaController.depositoCuenta(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(String.join("\n", errors), response.getBody().getMessage()); 
    }
}
