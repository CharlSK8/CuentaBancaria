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

    @Override
    public Result<TokenResponse, String> refreshToken(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return createErrorResult("Invalid authorization header format", HttpStatus.BAD_REQUEST);
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            return createErrorResult("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }

        final Optional<Usuario> userOptional = userRepository.findByCorreoAndActivoTrue(userEmail);

        if(userOptional.isEmpty()){
            return createErrorResult("User not found", HttpStatus.NOT_FOUND);
        }
        Usuario usuario = userOptional.get();

        if(!jwtService.isTokenValid(refreshToken, usuario)){
            return createErrorResult("Invalid or expired refresh token", HttpStatus.UNAUTHORIZED);
        }

        final Optional<Token> optionalToken = tokenRepository.findByToken(refreshToken);
        if(optionalToken.isEmpty()){
            return createErrorResult("Token not found", HttpStatus.BAD_REQUEST);
        }

        Token token = optionalToken.get();
        if (token.isExpired() || token.isRevoked()) {
            return createErrorResult("The token has expired or has been revoked.", HttpStatus.UNAUTHORIZED);
        }

        final String accessToken = jwtService.generateToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accessToken);
        return Result.success(new TokenResponse(accessToken, refreshToken));

    }

    @Override
    public Result<String, String> logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.failure(List.of("Invalid authorization header format"), HttpStatus.BAD_REQUEST);
        }
        final String jwtToken = authHeader.substring(7);
        final Optional<Token> optionalToken = tokenRepository.findByToken(jwtToken);
        if(optionalToken.isEmpty()){
            return Result.failure(Collections.singletonList("Token not found"), HttpStatus.BAD_REQUEST);
        }
        Token token = optionalToken.get();
        token.setRevoked(true);
        token.setExpired(true);
        tokenRepository.save(token);
        SecurityContextHolder.clearContext();
        return Result.success("Logout successful");
    }

    private void saveUserToken(Usuario usuario, String  jwtToken) {
        var token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .type(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Usuario usuario) {
        final List<Token> validUserToken = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUsuarioId(usuario.getId());
        if (!validUserToken.isEmpty()) {
            for (final Token token : validUserToken) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserToken);
        }

    }

    private Result<TokenResponse, String> createErrorResult(String errorMessage, HttpStatus httpStatus) {
        return Result.failure(Collections.singletonList(errorMessage), httpStatus);
    }

    private Usuario buildCliente(RegistrarUsuarioRequestDTO registrarUsuarioRequestDTO){
        registrarUsuarioRequestDTO.setContrasena(passwordEncoder.encode(registrarUsuarioRequestDTO.getContrasena()));
        return usuarioMapper.toUsuario(registrarUsuarioRequestDTO);
    }

}
