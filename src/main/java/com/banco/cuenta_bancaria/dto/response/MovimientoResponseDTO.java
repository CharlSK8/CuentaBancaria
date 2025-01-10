package com.banco.cuenta_bancaria.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoResponseDTO {

    private LocalDateTime fechaMovimiento;
    private BigDecimal monto;
    private String tipoMovimiento;

}
