package com.desafio.ada.prospect.conversores.json;

import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaDto;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonConversor {

    private final ObjectMapper objectMapper;

    public JsonConversor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String converter(PessoaFisicaDto pessoaFisicaDto)
            throws JsonProcessingException
    {
        final String json = this.objectMapper.writeValueAsString(pessoaFisicaDto);
        return  json;
    }

    public String converter(PessoaJuridicaDto pessoaJuridicaDto)
            throws JsonProcessingException
    {
        final String json = this.objectMapper.writeValueAsString(pessoaJuridicaDto);
        return json;
    }
}
