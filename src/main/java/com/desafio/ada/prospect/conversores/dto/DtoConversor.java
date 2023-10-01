package com.desafio.ada.prospect.conversores.dto;

import com.desafio.ada.prospect.conversores.atributo.MerchantCategoryConverter;
import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaDto;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaRequest;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaDto;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaRequest;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class DtoConversor {

    private ModelMapper mapper;

    public DtoConversor(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PessoaFisicaDto converterParaDto(PessoaFisicaRequest pessoaFisicaRequest)
    {
        final Converter<String, MerchantCategory> conversor = new MerchantCategoryConverter();
        final TypeMap<PessoaFisicaRequest, PessoaFisicaDto> mapaPropriedade = this.mapper.getTypeMap(PessoaFisicaRequest.class, PessoaFisicaDto.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaFisicaRequest, PessoaFisicaDto> novoMapaPropriedade = this.mapper.createTypeMap(PessoaFisicaRequest.class, PessoaFisicaDto.class);
            novoMapaPropriedade.addMappings(
                    mapper -> mapper
                            .using(conversor)
                            .map(PessoaFisicaRequest::getCodigoCategoria, PessoaFisicaDto::setMerchantCategory)
            );
        }
        return mapper.map(pessoaFisicaRequest, PessoaFisicaDto.class);
    }

    public PessoaJuridicaDto converterParaDto(PessoaJuridicaRequest pessoaJuridicaRequest) {
        final Converter<String, MerchantCategory> conversor = new MerchantCategoryConverter();
        final TypeMap<PessoaJuridicaRequest, PessoaJuridicaDto> mapaPropriedade = this.mapper.getTypeMap(PessoaJuridicaRequest.class, PessoaJuridicaDto.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaJuridicaRequest, PessoaJuridicaDto> novoMapaPropriedade = this.mapper.createTypeMap(PessoaJuridicaRequest.class, PessoaJuridicaDto.class);
            novoMapaPropriedade.addMappings(
                    mapper -> mapper
                            .using(conversor)
                            .map(PessoaJuridicaRequest::getCodigoCategoria, PessoaJuridicaDto::setMerchantCategory)
            );
        }
        return mapper.map(pessoaJuridicaRequest, PessoaJuridicaDto.class);
    }
}
