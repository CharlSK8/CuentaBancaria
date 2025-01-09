package com.banco.cuenta_bancaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.service.IUsuarioService;
import com.banco.cuenta_bancaria.util.Result;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseDTO<String>> registrarUsuario(@Valid @RequestBody RegistrarUsuarioRequestDTO request) {
        Result<String, String> result = usuarioService.registrarUsuario(request);

        return result.isSuccess()
					? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<String>builder()
							.message(result.getValue())
							.code(HttpStatus.OK.value()).response(null).build())
					: ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseDTO.<String>builder()
							.message(String.join("\n", result.getErrors()))
							.code(result.getStatusCode().value()).response(null).build());
    }
    @PostMapping("/actualizar/{id}")
    public ResponseEntity<ResponseDTO<String>> actualizarUsuario(@PathVariable("id") Long id, @RequestBody ActualizarUsuarioRequestDTO request) {
        Result<String, String> result = usuarioService.actualizarUsuario(id, request);
        return result.isSuccess()
					? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<String>builder()
							.message(result.getValue())
							.code(HttpStatus.OK.value()).response(null).build())
					: ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseDTO.<String>builder()
							.message(String.join("\n", result.getErrors()))
							.code(result.getStatusCode().value()).response(null).build());
    }

    @PostMapping("/inactivar/{id}")
    public ResponseEntity<ResponseDTO<String>> inactivarUsuario(@PathVariable("id") Long id) {
        Result<String, String> result = usuarioService.inactivarUsuario(id);
        return result.isSuccess()
					? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<String>builder()
							.message(result.getValue())
							.code(HttpStatus.OK.value()).response(null).build())
					: ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ResponseDTO.<String>builder()
							.message(String.join("\n", result.getErrors()))
							.code(result.getStatusCode().value()).response(null).build());
    }

}
