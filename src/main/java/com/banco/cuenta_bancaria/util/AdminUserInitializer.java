package com.banco.cuenta_bancaria.util;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.enums.Roles;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void initializeAdminUser() {
        String adminEmail = "admin@gmail.com";

        boolean adminExists = usuarioRepository.findByCorreoAndActivoTrue(adminEmail).isPresent();

        if (!adminExists) {
            Usuario admin = Usuario.builder()
                .numeroIdetificacion("123456789")
                .nombre("Admin")
                .correo(adminEmail)
                .contrasena(passwordEncoder.encode("Admin123!"))
                .activo(true)
                .roles(Set.of(Roles.ADMIN))
                .build();
            usuarioRepository.save(admin);
            log.info("Usuario administrador creado exitosamente.");
        } else {
            log.warn("El usuario administrador ya existe.");
        }
    }
}
