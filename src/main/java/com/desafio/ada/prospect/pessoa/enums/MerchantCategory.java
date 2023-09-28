package com.desafio.ada.prospect.pessoa.enums;

public enum MerchantCategory {

    RAILROAD_FREIGHT("4011");

    private String codigoCategoria;

    MerchantCategory(String codigo) {
        this.codigoCategoria = codigo;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

}