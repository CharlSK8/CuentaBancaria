package com.banco.cuenta_bancaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaBancariaEventMessage implements Serializable {

    private static final long serialVersionUID = 1l;

    private String eventType; // "REGISTER"
    private int numeroCuenta;
    private Long numeroIdentificacion;
    private BigDecimal saldo;
    private String tipoCuenta;
    private boolean activa;
}
