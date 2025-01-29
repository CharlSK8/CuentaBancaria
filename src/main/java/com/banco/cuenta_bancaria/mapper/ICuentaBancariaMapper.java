package com.banco.cuenta_bancaria.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.banco.cuenta_bancaria.dto.request.CrearCuentaRequestDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.util.CuentaBancariaUtil;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICuentaBancariaMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroCuenta", expression = "java(cuentaService.generarNumeroCuenta())")
    @Mapping(target = "saldo", source = "request.saldo")
    @Mapping(target = "tipoCuenta", source = "request.tipoCuenta")
    @Mapping(target = "usuario", source = "usuario")
    CuentaBancaria toEntity(CrearCuentaRequestDTO request, Usuario usuario, @Context CuentaBancariaUtil cuentaService);

}
