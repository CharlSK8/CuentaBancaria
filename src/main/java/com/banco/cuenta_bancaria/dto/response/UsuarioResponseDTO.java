package com.banco.cuenta_bancaria.dto.response;

import java.util.List;
import java.util.Set;

import com.banco.cuenta_bancaria.entity.CuentaBancaria;
import com.banco.cuenta_bancaria.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private String numeroIdetificacion;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String contrasena;
    private boolean activo;
    private List<CuentaBancariaResponseDTO> cuentasBancarias;
    private Set<Roles> roles;

}
