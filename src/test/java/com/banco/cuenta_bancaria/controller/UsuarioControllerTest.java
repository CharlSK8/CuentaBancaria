package com.banco.cuenta_bancaria.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.service.IUsuarioService;
import com.banco.cuenta_bancaria.util.Result;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void actualizarUsuario_CuandoEsExitoso_RetornaOk() {

        Long id = 1L;
        ActualizarUsuarioRequestDTO request = new ActualizarUsuarioRequestDTO();
        Result<String, String> result = Result.success("Usuario actualizado exitosamente");
        when(usuarioService.actualizarUsuario(id, request)).thenReturn(result);

        ResponseEntity<ResponseDTO<String>> response = usuarioController.actualizarUsuario(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario actualizado exitosamente", response.getBody().getMessage());
        assertEquals(HttpStatus.OK.value(), response.getBody().getCode());
    }

    @Test
    void inactivarUsuario_CuandoEsExitoso_RetornaOk() {
        Long id = 1L;
        Result<String, String> result = Result.success("Usuario inactivado exitosamente");
        when(usuarioService.inactivarUsuario(id)).thenReturn(result);

        ResponseEntity<ResponseDTO<String>> response = usuarioController.inactivarUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario inactivado exitosamente", response.getBody().getMessage());
        assertEquals(HttpStatus.OK.value(), response.getBody().getCode());
    }
}
