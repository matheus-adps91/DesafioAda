package com.desafio.ada.prospect.pessoa.fisica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PessoaFisicaRepository
        extends JpaRepository<PessoaFisica, Long> {

    Optional<PessoaFisica> findByUuid(UUID uuid);

}
