package com.banco.cuenta_bancaria.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CrearCuentaRequestDTO {

    @NotBlank(message = "El campo 'saldo' es obligatorio")
    private BigDecimal saldo;
    @NotBlank(message = "El campo 'tipoCuenta' es obligatorio")
    @Pattern(regexp = "^(CORRIENTE|AHORROS)$", message = "El tipo de cuenta debe ser 'CORRIENTE' o 'AHORROS'")
    private String tipoCuenta;
    @NotBlank(message = "El campo 'numeroIdetificacion' es obligatorio")
    private String numeroIdetificacion;
    
}

