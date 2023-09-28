package com.desafio.ada.prospect.pessoa;

import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Pessoa {

    private String cpf;
    @Enumerated(EnumType.STRING)
    private MerchantCategory merchantCategory;
    private String nome;
    private String email;

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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setMerchantCategory(MerchantCategory merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String obterMerchantCategoryNome() {
        return merchantCategory.name();
    }

}