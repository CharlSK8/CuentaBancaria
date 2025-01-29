package com.banco.cuenta_bancaria.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.CuentaBancariaResponseDTO;
import com.banco.cuenta_bancaria.dto.response.UsuarioResponseDTO;
import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.entity.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioMapper {

    Usuario toUsuario(RegistrarUsuarioRequestDTO registrarUsuarioRequestDTO);
    
    @Mapping(source = "numeroCuenta", target = "numeroCuenta")
    @Mapping(source = "saldo", target = "saldo")
    @Mapping(source = "tipoCuenta", target = "tipoCuenta")
    @Mapping(source = "activa", target = "activa")
    CuentaBancariaResponseDTO toCuentaBancariaResponseDTO(CuentaBancaria cuentaBancaria);

    List<CuentaBancariaResponseDTO> toCuentaBancariaResponseDTOs(List<CuentaBancaria> cuentasBancarias);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

}
