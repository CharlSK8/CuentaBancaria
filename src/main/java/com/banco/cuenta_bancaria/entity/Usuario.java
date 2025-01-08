package com.banco.cuenta_bancaria.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroIdetificacion;
    private String nombre;
    private String apellido;
    private String telefono;
    @Column(unique=true)
    private String correo;
    private String contrasena;
    private boolean activo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<CuentaBancaria> cuentasBancarias;

    @PrePersist
    public void init() {
        activo = true;
    }

}
