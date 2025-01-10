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

    /**
     * Autentica un usuario y genera un token de acceso.
     *
     * Este método permite a un usuario iniciar sesión proporcionando las
     * credenciales necesarias a través de un objeto {@link LoginRequestDTO}.
     * Si las credenciales son válidas, se genera y devuelve un token de acceso
     * que el usuario puede utilizar para autenticar futuras solicitudes.
     *
     * @param request el objeto que contiene las credenciales del usuario,
     *                que deben ser validadas mediante la anotación {@link Valid}.
     * @return una instancia de {@link ResponseEntity<ResponseDTO>} que contiene
     *         la respuesta con el estado de la operación y el token de acceso generado.
     * @throws Exception si ocurre algún error durante el proceso de autenticación del usuario.
     */
    @Operation(summary = "Autenticar usuario", description = "Permite a un usuario iniciar sesión y obtener un token de acceso.")
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> authenticate(@Valid @RequestBody final LoginRequestDTO request) {
       try {
           final Result<TokenResponse, String> result = service.login(request);
           return getResponseDTOResponseEntity(result);
       }catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                   .message(Constants.MESSAGE_ERROR)
                   .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .response(e.getMessage())
                   .build());
       }
    }

    /**
     * Refresca el token de acceso utilizando un token de refresco.
     *
     * Este método permite a un usuario obtener un nuevo token de acceso mediante
     * la provisión de un token de refresco que se pasa a través del encabezado
     * de autorización {@link HttpHeaders#AUTHORIZATION}. Si el token de refresco
     * es válido, se genera y devuelve un nuevo token de acceso.
     *
     * @param authHeader el encabezado de autorización que contiene el token de refresco.
     *                   El token debe estar presente en el formato "Bearer {token}".
     * @return una instancia de {@link ResponseEntity<ResponseDTO>} que contiene
     *         la respuesta con el nuevo token de acceso generado.
     * @throws Exception si ocurre algún error durante el proceso de refresco del token.
     */
    @Operation(summary = "Refrescar token de acceso", description = "Permite refrescar un token de acceso utilizando un token de refresco.")
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            final Result<TokenResponse, String> result = service.refreshToken(authHeader);
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
