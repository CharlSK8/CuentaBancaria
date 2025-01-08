package com.banco.cuenta_bancaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.cuenta_bancaria.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNumeroIdetificacionAndActivoTrue(String numeroIdetificacion);
    Optional<Usuario> findByIdAndActivoTrue(Long id);

}
