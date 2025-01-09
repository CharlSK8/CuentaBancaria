package com.banco.cuenta_bancaria.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.banco.cuenta_bancaria.dto.response.MovimientoResponseDTO;
import com.banco.cuenta_bancaria.entity.Movimiento;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMovimientosMapper {

    MovimientoResponseDTO toMovimientoResponseDTO(Movimiento movimiento);

    List<MovimientoResponseDTO> toMovimientoResponseDTOList(List<Movimiento> movimientos);

}
