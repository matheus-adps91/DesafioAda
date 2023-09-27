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

}
