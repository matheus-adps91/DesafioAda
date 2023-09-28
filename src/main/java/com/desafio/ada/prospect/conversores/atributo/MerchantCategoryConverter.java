package com.desafio.ada.prospect.conversores.atributo;

import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import org.modelmapper.AbstractConverter;

import java.util.Arrays;

public class MerchantCategoryConverter extends AbstractConverter<String, MerchantCategory> {

    @Override
    protected MerchantCategory convert(String codigoCategoria) {
        return Arrays.stream(MerchantCategory.values())
                .filter( merchantCategory -> merchantCategory
                        .getCodigoCategoria().equals(codigoCategoria))
                .findAny()
                .orElseThrow( () -> new RecursoNaoEncontradoException(
                        "Não foi possível identificar a merchant category pelo código " + codigoCategoria));
    }
}
