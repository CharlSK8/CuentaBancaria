package com.banco.cuenta_bancaria.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import com.banco.cuenta_bancaria.dto.request.ActualizarUsuarioRequestDTO;
import com.banco.cuenta_bancaria.dto.response.UsuarioResponseDTO;
import com.banco.cuenta_bancaria.entity.Usuario;
import com.banco.cuenta_bancaria.mapper.IUsuarioMapper;
import com.banco.cuenta_bancaria.repository.IUsuarioRepository;
import com.banco.cuenta_bancaria.util.Result;


class UsuarioServiceImplTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private IUsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarUsuario() {
        Long id = 1L;
        ActualizarUsuarioRequestDTO request = new ActualizarUsuarioRequestDTO();
        request.setNombre("NuevoNombre");
        request.setApellido("NuevoApellido");
        request.setCorreo("nuevo@correo.com");
        request.setTelefono("123456789");

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Result<String, String> result = usuarioServiceImpl.actualizarUsuario(id, request);

        assertEquals("Usuario actualizado correctamente", result.getValue());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testActualizarUsuarioNotFound() {
        Long id = 1L;
        ActualizarUsuarioRequestDTO request = new ActualizarUsuarioRequestDTO();

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.empty());

        Result<String, String> result = usuarioServiceImpl.actualizarUsuario(id, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testInactivarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Result<String, String> result = usuarioServiceImpl.inactivarUsuario(id);

        assertEquals("Usuario inactivado correctamente", result.getValue());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testInactivarUsuarioNotFound() {
        Long id = 1L;

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.empty());

        Result<String, String> result = usuarioServiceImpl.inactivarUsuario(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testConsultarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponseDTO(any(Usuario.class))).thenReturn(responseDTO);

        Result<UsuarioResponseDTO, String> result = usuarioServiceImpl.consultarUsuario(id);

        assertEquals(responseDTO, result.getValue());
    }

    @Test
    void testConsultarUsuarioNotFound() {
        Long id = 1L;

        when(usuarioRepository.findByIdAndActivoTrue(id)).thenReturn(Optional.empty());

        Result<UsuarioResponseDTO, String> result = usuarioServiceImpl.consultarUsuario(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}