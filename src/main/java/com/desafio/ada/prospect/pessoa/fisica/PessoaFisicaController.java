package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.conversores.dto.DtoConversor;
import com.desafio.ada.prospect.conversores.json.JsonConversor;
import com.desafio.ada.prospect.conversores.response.ResponseConversor;
import com.desafio.ada.prospect.utilitarios.Constantes;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa-fisica")
public class PessoaFisicaController {

    private final PessoaFisicaService pessoaFisicaService;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ResponseConversor responseConversor;
    private final DtoConversor dtoConversor;
    private final JsonConversor jsonConversor;
    @Value("${aws.queue-name}")
    private String queueName;

    public PessoaFisicaController(
            PessoaFisicaService pessoaFisicaService,
            ResponseConversor responseConversor,
            QueueMessagingTemplate queueMessagingTemplate,
            DtoConversor dtoConversor,
            JsonConversor jsonConversor)
    {
        this.pessoaFisicaService = pessoaFisicaService;
        this.responseConversor = responseConversor;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.dtoConversor = dtoConversor;
        this.jsonConversor = jsonConversor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaFisicaResponse cadastrarPessoaFisica(
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest)
            throws JsonProcessingException
    {
        final PessoaFisicaDto pessoaFisicaDto = this.dtoConversor.converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaSalva = this.pessoaFisicaService.cadastrarPessoa(pessoaFisicaDto);
        final String sPessoaFisicaDto = this.jsonConversor.converter(pessoaFisicaDto);
        final Message<String> message = MessageBuilder
                .withPayload(sPessoaFisicaDto)
                .setHeader(Constantes.TIPO_DADO, Constantes.PESSOA_FISICA_DTO)
                .build();
        this.queueMessagingTemplate.send(queueName, message);
        final PessoaFisicaResponse pessoaFisicaResponse = this.responseConversor.converterParaResponse(pessoaSalva);
        return pessoaFisicaResponse;
    }

    @GetMapping
    public List<PessoaFisicaResponse> listarPessoasFisica()
    {
        final List<PessoaFisica> pessoasFisicas = this.pessoaFisicaService.listarPessoasFisicas();
        final List<PessoaFisicaResponse> pessoasFisicaResponse = pessoasFisicas.stream()
                .map(pessoaFisica -> this.responseConversor.converterParaResponse(pessoaFisica))
                .toList();
        return pessoasFisicaResponse;
    }

    @GetMapping("{uuid}")
    public PessoaFisicaResponse obterPessoaFisicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaFisica pessoaFisica = this.pessoaFisicaService.obterPessoaFisicaPorId(uuid);
        final PessoaFisicaResponse pessoaFisicaResposta = this.responseConversor.converterParaResponse(pessoaFisica);
        return pessoaFisicaResposta;
    }

    @PutMapping("{uuid}")
    public PessoaFisicaResponse atualizarPessoaFisicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest)
    {
        final PessoaFisicaDto pessoaFisicaDto = this.dtoConversor.converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaFisicaAtualizada = this.pessoaFisicaService.atualizarPessoaFisicaPorId(uuid, pessoaFisicaDto);
        final PessoaFisicaResponse pessoaFisicaResponse = this.responseConversor.converterParaResponse(pessoaFisicaAtualizada);
        return pessoaFisicaResponse;
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaFisica(
            @PathVariable UUID uuid)
    {
        this.pessoaFisicaService.deletarPessoaFisica(uuid);
    }

}