package com.desafio.ada.prospect.conversores.response;

import com.desafio.ada.prospect.pessoa.fisica.PessoaFisica;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaResponse;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridica;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ResponseConversor {

    private ModelMapper mapper;

    public ResponseConversor(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PessoaFisicaResponse converterParaResponse(PessoaFisica pessoaSalva)
    {
        final TypeMap<PessoaFisica, PessoaFisicaResponse> mapaPropriedade = this.mapper.getTypeMap(PessoaFisica.class, PessoaFisicaResponse.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaFisica, PessoaFisicaResponse> propertyMap = this.mapper.createTypeMap(PessoaFisica.class, PessoaFisicaResponse.class);
            propertyMap.addMapping(PessoaFisica::obterMerchantCategoryNome, PessoaFisicaResponse::setMerchantCategory);
        }
        return this.mapper.map(pessoaSalva, PessoaFisicaResponse.class);
    }

    public PessoaJuridicaResponse converterParaResponse(PessoaJuridica pessoaSalva) {
        final TypeMap<PessoaJuridica, PessoaJuridicaResponse> mapaPropriedade = this.mapper.getTypeMap(PessoaJuridica.class, PessoaJuridicaResponse.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaJuridica, PessoaJuridicaResponse> novoMapaPropriedade = this.mapper.createTypeMap(PessoaJuridica.class, PessoaJuridicaResponse.class);
            novoMapaPropriedade.addMapping(PessoaJuridica::obterMerchantCategoryNome, PessoaJuridicaResponse::setMerchantCategory);
        }
        return this.mapper.map(pessoaSalva, PessoaJuridicaResponse.class);
    }
}
