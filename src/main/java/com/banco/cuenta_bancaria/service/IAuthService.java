package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.dto.request.LoginRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.TokenResponse;
import com.banco.cuenta_bancaria.dto.response.UserResponse;
import com.banco.cuenta_bancaria.util.Result;

public interface IAuthService {
    Result<UserResponse, String> register(RegistrarUsuarioRequestDTO clienteRegisterRequestDTOst);
    Result<TokenResponse, String> login(LoginRequestDTO loginRequest);
    Result<TokenResponse, String> refreshToken(String authHeader);
    Result<String, String> logout(String authHeader);
}
