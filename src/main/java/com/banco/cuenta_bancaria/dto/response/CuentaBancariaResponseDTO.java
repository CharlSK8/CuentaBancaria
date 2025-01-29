package com.banco.cuenta_bancaria.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaBancariaResponseDTO {
    
    private int numeroCuenta;
    private BigDecimal saldo;
    private String tipoCuenta;
    private boolean activa;

}
