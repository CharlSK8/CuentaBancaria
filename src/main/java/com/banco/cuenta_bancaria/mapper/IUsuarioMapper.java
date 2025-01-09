package com.banco.cuenta_bancaria.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.entity.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioMapper {

    Usuario toUsuario(RegistrarUsuarioRequestDTO registrarUsuarioRequestDTO);

}
