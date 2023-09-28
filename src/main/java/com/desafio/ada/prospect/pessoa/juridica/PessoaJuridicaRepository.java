package com.desafio.ada.prospect.pessoa.juridica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PessoaJuridicaRepository
        extends JpaRepository<PessoaJuridica, Long> {

    Optional<PessoaJuridica> findByUuid(UUID uuid);

}