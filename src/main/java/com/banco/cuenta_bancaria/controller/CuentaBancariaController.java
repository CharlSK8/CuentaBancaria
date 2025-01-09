package com.banco.cuenta_bancaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.DepositoCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RetiroCuentaRequestDTO;
import com.banco.cuenta_bancaria.dto.request.SaldoActualRequestDTO;
import com.banco.cuenta_bancaria.dto.response.ResponseDTO;
import com.banco.cuenta_bancaria.dto.response.SaldoActualResponseDTO;
import com.banco.cuenta_bancaria.service.ICuentaBancariaService;
import com.banco.cuenta_bancaria.util.Result;

@RestController
@RequestMapping("/api/v1/cuenta-bancaria")
@CrossOrigin(origins = "http://localhost:4200")
public class CuentaBancariaController {

    private ICuentaBancariaService cuentaBancariaService;

    public CuentaBancariaController(ICuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    @PostMapping("/crear-cuenta")
    public ResponseEntity<ResponseDTO<String>> crearCuenta(@RequestBody CrearCuentaRequestDTO crearCuentaRequestDTO) {
        Result<String, String> result = cuentaBancariaService.crearCuentaBancaria(crearCuentaRequestDTO);
        return result.isSuccess()
                ? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<String>builder()
                        .message(result.getValue())
                        .code(HttpStatus.OK.value()).response(null).build())
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDTO.<String>builder()
                                .message(String.join("\n", result.getErrors()))
                                .code(result.getStatusCode().value()).response(null).build());
    }
    @GetMapping("/mostrar-saldo-actual/{numeroCuenta}")
    public ResponseEntity<ResponseDTO> mostrarSaldo(@PathVariable("numeroCuenta") int numeroCuenta) {
        Result<SaldoActualResponseDTO, String> result = cuentaBancariaService.mostrarSaldoActual(numeroCuenta);
        return result.isSuccess()
                ? ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<SaldoActualResponseDTO>builder()
                        .message("Se ha mostrado el saldo correctamente")
                        .code(HttpStatus.OK.value()).response(result.getValue()).build())
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDTO.<String>builder()
                                .message(String.join("\n", result.getErrors()))
                                .code(result.getStatusCode().value()).response(null).build());
        }

    @PostMapping("/retiro-cuenta")
    public ResponseEntity<ResponseDTO<String>> retiroCuenta(@RequestBody RetiroCuentaRequestDTO retiroCuentaRequestDTO) {
        Result<String, String> result = cuentaBancariaService.retiroCuenta(retiroCuentaRequestDTO);
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
