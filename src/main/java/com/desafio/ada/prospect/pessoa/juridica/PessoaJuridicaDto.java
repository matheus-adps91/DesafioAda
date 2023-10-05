package com.desafio.ada.prospect.pessoa.juridica;

import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridicaDto {

    private UUID uuid;
    private String cnpj;
    private String razaoSocial;
    private String cpf;
    private MerchantCategory merchantCategory;
    private String nome;
    private String email;

    public UUID getUuid() {
        return uuid;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
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

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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
