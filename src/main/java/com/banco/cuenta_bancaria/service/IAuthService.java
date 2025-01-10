package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.LoginRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.TokenResponse;
import com.banco.cuenta_bancaria.util.Result;

public interface IAuthService {
    Result<TokenResponse, String> register(RegistrarUsuarioRequestDTO clienteRegisterRequestDTOst);
    Result<TokenResponse, String> login(LoginRequestDTO loginRequest);
}
