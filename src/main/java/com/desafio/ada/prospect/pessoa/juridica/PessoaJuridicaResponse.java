package com.desafio.ada.prospect.pessoa.juridica;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PessoaJuridicaResponse {
    private UUID uuid;
    private String cnpj;
    private String razaoSocial;
    private String cpf;
    private String sMerchantCategory;
    private String nome;
    private String email;

    public void setMerchantCategory(String sMerchantCategory) {
        this.sMerchantCategory = sMerchantCategory;
    }
}
