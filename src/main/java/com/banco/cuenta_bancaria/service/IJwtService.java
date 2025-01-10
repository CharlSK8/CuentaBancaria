package com.banco.cuenta_bancaria.service;

import com.banco.cuenta_bancaria.entity.Usuario;

public interface IJwtService {
    String generateToken(final Usuario usuario);
    String generateRefreshToken(final Usuario usuario);
    String extractUsername(String token);
    boolean isTokenValid(String token, Usuario usuario);
}
