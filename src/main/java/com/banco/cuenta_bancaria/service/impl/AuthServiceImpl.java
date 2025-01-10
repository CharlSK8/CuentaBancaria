package com.banco.cuenta_bancaria.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banco.cuenta_bancaria.dto.request.LoginRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.TokenResponse;
import com.banco.cuenta_bancaria.entity.Token;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.mapper.IUsuarioMapper;
import com.banco.cuenta_bancaria.repository.ITokenRepository;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;
import com.banco.cuenta_bancaria.service.IAuthService;
import com.banco.cuenta_bancaria.service.IJwtService;
import com.banco.cuenta_bancaria.util.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final IUsuarioRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUsuarioMapper usuarioMapper;

    @Override
    public Result<TokenResponse, String> register(RegistrarUsuarioRequestDTO registrarUsuarioRequestDTO) {
        var user = buildCliente(registrarUsuarioRequestDTO);
        var userSaved = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(userSaved, jwtToken);
        return Result.success(new TokenResponse(jwtToken, refreshToken));
    }

    @Override
    public Result<TokenResponse, String> login(LoginRequestDTO loginRequest) {

        final Optional<Usuario> userOptional = userRepository.findByCorreoAndActivoTrue(loginRequest.getEmail());

        if(userOptional.isEmpty()){
            return createErrorResult("Email not found", HttpStatus.NOT_FOUND);
        }
        Usuario usuario = userOptional.get();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    usuario.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .toList()));
        } catch (AuthenticationException e) {
            return createErrorResult("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateRefreshToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, jwtToken);
        return Result.success(new TokenResponse(jwtToken, refreshToken));
    }
}
