package com.banco.cuenta_bancaria.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioRequestDTO {

    private String numeroIdetificacion;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;


}
