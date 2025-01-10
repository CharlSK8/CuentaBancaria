package com.banco.cuenta_bancaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.cuenta_bancaria.entity.Token;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUsuarioId(Long userId);
    Optional<Token> findByToken(String jwtToken);

}
