package com.desafio.ada.prospect.atendimento;

import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.fila.Fila;
import com.desafio.ada.prospect.pessoa.Pessoa;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisica;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridica;
import com.desafio.ada.prospect.utilitarios.Constantes;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoService {

    public Pessoa atenderProximoFila() {
        Fila fila = Fila.obterInstancia();
        if (fila.estaVazia()){
            throw new RecursoNaoEncontradoException(Constantes.FILA_ATENDIMENTO_VAZIA);
        }
        Object cliente = fila.remover();
        if (cliente instanceof PessoaJuridica) {
            return (PessoaJuridica) cliente;
        }
        if (cliente instanceof PessoaFisica) {
            return (PessoaFisica) cliente;
        }
        throw new IllegalArgumentException(Constantes.ERRO_PROCESSAMENTO_FILA);
    }
}
