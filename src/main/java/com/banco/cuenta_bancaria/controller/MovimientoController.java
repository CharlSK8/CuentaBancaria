package com.banco.cuenta_bancaria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.service.IMovimientoService;
import com.banco.cuenta_bancaria.util.Constants;
import com.banco.cuenta_bancaria.util.Result;

@RestController
@RequestMapping("/api/v1/movimiento")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimientoController {

    private IMovimientoService movimientoService;

    public MovimientoController(IMovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/movimientos/{numeroCuenta}")
    public ResponseEntity<ResponseDTO<?>> mostrarMovimientos(@PathVariable("numeroCuenta") int numeroCuenta) {
        Result<List<MovimientoResponseDTO>, String> result = movimientoService.mostrarMovimientos(numeroCuenta);
        try{
            return result.isSuccess()
            ? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<List<MovimientoResponseDTO>>builder()
                    .message("Se ha mostrado los movimientos correctamente")
                    .code(HttpStatus.OK.value()).response(result.getValue()).build())
            : ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDTO.<String>builder()
                    .message(String.join("\n", result.getErrors()))
                    .code(result.getStatusCode().value()).response(null).build());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder()
                    .message(Constants.MESSAGE_ERROR)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .response(e.getMessage())
                    .build());
        }
    }
}
