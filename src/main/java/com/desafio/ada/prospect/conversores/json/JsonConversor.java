package com.desafio.ada.prospect.conversores.json;

import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaDto;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConversor {

    public static String converter(PessoaFisicaDto pessoaFisicaDto)
            throws JsonProcessingException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(pessoaFisicaDto);
        return  json;
    }

    public static String converter(PessoaJuridicaDto pessoaJuridicaDto)
            throws JsonProcessingException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(pessoaJuridicaDto);
        return json;
    }
}
