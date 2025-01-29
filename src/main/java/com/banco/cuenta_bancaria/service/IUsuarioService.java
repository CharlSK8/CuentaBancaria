package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.UsuarioResponseDTO;
import com.banco.cuenta_bancaria.util.Result;

public interface IUsuarioService {

    Result<String, String> actualizarUsuario(Long id, ActualizarUsuarioRequestDTO request);
    Result<String, String> inactivarUsuario(Long id);
    Result<UsuarioResponseDTO, String> consultarUsuario(Long id);
}
