package com.desafio.ada.prospect.atendimento;

import com.desafio.ada.prospect.pessoa.Pessoa;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {

    private AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @GetMapping
    public Pessoa atenderProximoFila() {
        return atendimentoService.atenderProximoFila();
    }
}
