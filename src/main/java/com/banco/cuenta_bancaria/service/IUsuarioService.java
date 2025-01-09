package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.util.Result;

public interface IUsuarioService {

    Result<String, String> registrarUsuario(RegistrarUsuarioRequestDTO request);
    Result<String, String> actualizarUsuario(Long id, ActualizarUsuarioRequestDTO request);
    Result<String, String> inactivarUsuario(Long id);
}
