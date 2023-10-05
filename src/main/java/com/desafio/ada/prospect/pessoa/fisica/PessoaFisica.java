package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@Entity
public class PessoaFisica extends Pessoa  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private UUID uuid;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

}