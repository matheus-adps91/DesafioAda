package com.desafio.ada.prospect.pessoa.juridica;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridicaRequest {

    private UUID uuid;
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;
    private String razaoSocial;
    @CPF(message = "CPF inválido")
    private String cpf;
    @Size(max = 4, min = 4, message = "Codigo da categoria de mercado deve conter 4 caracteres")
    private String codigoCategoria;
    @Size(max = 50, message = "Nome deve conter no máximo 50 caracteres")
    private String nome;
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "Formato de email inválido")
    private String email;

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

}
