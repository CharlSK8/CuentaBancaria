package com.banco.cuenta_bancaria.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuditorConfig {

    private final IUsuarioRepository usuarioRepository;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(this::obtenerNombreUsuario);
    }

    private String obtenerNombreUsuario(Authentication authentication) {
        return usuarioRepository.findByCorreoAndActivoTrue(authentication.getName())
                .map(Usuario::getNombre)
                .orElse("default");
    }

}
