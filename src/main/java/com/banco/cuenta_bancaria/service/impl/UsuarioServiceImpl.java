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
    private final IUsuarioMapper usuarioMapper;
	
    @Override
    public Result<String, String> actualizarUsuario(Long id, ActualizarUsuarioRequestDTO request) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAndActivoTrue(id);
        if(!usuario.isPresent()) {
            return Result.failure(List.of("Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }
        if(request.getNombre() != null) {
            usuario.get().setNombre(request.getNombre());
        }
        if(request.getApellido() != null) {
            usuario.get().setApellido(request.getApellido());
        }
        if(request.getCorreo() != null) {
            usuario.get().setCorreo(request.getCorreo());
        }
        if(request.getTelefono() != null) {
            usuario.get().setTelefono(request.getTelefono());
        }
        usuarioRepository.save(usuario.get());
        return Result.success("Usuario actualizado correctamente");
    }


    @Override
    public Result<String, String> inactivarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAndActivoTrue(id);
        if(!usuario.isPresent()) {
            return Result.failure(List.of("Usuario no encontrado"), HttpStatus.BAD_REQUEST);
        }
        usuario.get().setActivo(false);
        usuarioRepository.save(usuario.get());
        return Result.success("Usuario inactivado correctamente");
    }

}
