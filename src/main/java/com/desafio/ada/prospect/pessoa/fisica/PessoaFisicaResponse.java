package com.desafio.ada.prospect.pessoa.fisica;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaResponse {

    private UUID uuid;
    private String cpf;
    private String sMerchantCategory;
    private String nome;
    private String email;

    public void setMerchantCategory(String sMerchantCategory) {
        this.sMerchantCategory = sMerchantCategory;
    }
}
