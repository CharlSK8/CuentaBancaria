package com.banco.cuenta_bancaria.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.request.RegistrarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;
import com.banco.cuenta_bancaria.service.IUsuarioService;
import com.banco.cuenta_bancaria.util.Result;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService{

    private final IUsuarioRepository usuarioRepository;
	
	@Override
    public Result<String, String> registrarUsuario(RegistrarUsuarioRequestDTO request) {
		Optional<Usuario> usuario = usuarioRepository.findByNumeroIdetificacionAndActivoTrue(request.getNumeroIdetificacion());
		if(usuario.isPresent()) {
			return Result.failure(List.of("Usuario ya existe"), HttpStatus.BAD_REQUEST);
		}
        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .numeroIdetificacion(request.getNumeroIdetificacion())
                .build();
                
        usuarioRepository.save(nuevoUsuario);
        return Result.success("Usuario registrado correctamente");
    }
}
