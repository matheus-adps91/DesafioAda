package com.desafio.ada.prospect.configuracao;


import com.desafio.ada.prospect.fila.Fila;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InicializadorFila {

    @PostConstruct
    public void init() {
        Fila.obterInstancia();
    }
}
