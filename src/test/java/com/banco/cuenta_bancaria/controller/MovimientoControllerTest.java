package com.banco.cuenta_bancaria.controller;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.service.IMovimientoService;
import com.banco.cuenta_bancaria.util.Result;

class MovimientoControllerTest {

    @Mock
    private IMovimientoService movimientoService;

    @InjectMocks
    private MovimientoController movimientoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarMovimientos_CuandoExistenMovimientos_RetornaMovimientosExitosamente() {
        // Arrange
        int numeroCuenta = 123456;
        List<MovimientoResponseDTO> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoResponseDTO(/* agregar datos de prueba */));
        Result<List<MovimientoResponseDTO>, String> result = Result.success(movimientos);
        
        when(movimientoService.mostrarMovimientos(numeroCuenta)).thenReturn(result);

        // Act
        ResponseEntity<ResponseDTO<?>> response = movimientoController.mostrarMovimientos(numeroCuenta);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Se ha mostrado los movimientos correctamente", response.getBody().getMessage());
        assertEquals(HttpStatus.OK.value(), response.getBody().getCode());
        assertEquals(movimientos, response.getBody().getResponse());
    }

    @Test
    void mostrarMovimientos_CuandoNoExistenMovimientos_RetornaError() {
        // Arrange
        int numeroCuenta = 123456;
        String errorMessage = "No se encontraron movimientos para la cuenta especificada";
        Result<List<MovimientoResponseDTO>, String> result = Result.failure(List.of(errorMessage), HttpStatus.BAD_REQUEST);
        
        when(movimientoService.mostrarMovimientos(numeroCuenta)).thenReturn(result);

        // Act
        ResponseEntity<ResponseDTO<?>> response = movimientoController.mostrarMovimientos(numeroCuenta);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getCode());
        assertNull(response.getBody().getResponse());
    }
}
