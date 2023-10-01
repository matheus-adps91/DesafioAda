package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaDto {

    private UUID uuid;
    private String cpf;
    private MerchantCategory merchantCategory;
    private String nome;
    private String email;

    public UUID getUuid() {
        return uuid;
    }

    public String getCpf() {
        return cpf;
    }

    public MerchantCategory getMerchantCategory() {
        return merchantCategory;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setMerchantCategory(MerchantCategory merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}