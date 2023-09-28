package com.desafio.ada.prospect.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResponse {

    private String nomeClasse;
    private HttpStatus httpStatus;
    private List<String> mensagensErro;
    private LocalDateTime dataHoraFalha;

    public ErroResponse() { }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getMensagensErro() {
        return mensagensErro;
    }

    public LocalDateTime getDataHoraFalha() {
        return dataHoraFalha;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMensagensErro(List<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }

    public void setDataHoraFalha(LocalDateTime dataHoraFalha) {
        this.dataHoraFalha = dataHoraFalha;
    }
}
