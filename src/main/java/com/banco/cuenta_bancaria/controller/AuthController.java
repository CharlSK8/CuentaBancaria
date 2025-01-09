package com.banco.cuenta_bancaria.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.cuenta_bancaria.dto.request.LoginRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.dto.response.TokenResponse;
import com.banco.cuenta_bancaria.service.IAuthService;
import com.banco.cuenta_bancaria.util.Constants;
import com.banco.cuenta_bancaria.util.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Authentication", description = "Endpoints para autenticación de usuarios")
public class AuthController {

    private final IAuthService service;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Este método permite registrar un nuevo usuario proporcionando los datos
     * necesarios a través de un objeto {@link ClienteRegisterRequestDTO}.
     * Al registrar el usuario, el sistema genera un token de acceso que se
     * devuelve en la respuesta, lo cual permite que el usuario se autentique
     * en el sistema.
     *
     * @param clienteRegisterRequestDTO el objeto que contiene los datos necesariospara registrar al nuevo usuario.
     *                                  Debe ser validado mediante la anotación{@link Valid}.
     * @return una instancia de {@link ResponseEntity<ResponseDTO>} que contiene
     *         la respuesta con el estado de la operación y el token de acceso generado.
     * @throws Exception si ocurre algún error durante el proceso de registro del usuario.
     */
    @Operation(summary = "Registrar nuevo usuario", description = "Permite registrar un nuevo usuario y obtener un token de acceso.")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody RegistrarUsuarioRequestDTO clienteRegisterRequestDTO) {
        try {
            final Result<TokenResponse, String> result = service.register(clienteRegisterRequestDTO);
            return getResponseDTOResponseEntity(result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .message(Constants.MESSAGE_ERROR)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .response(e.getMessage())
                    .build());
        }

    }
    }
