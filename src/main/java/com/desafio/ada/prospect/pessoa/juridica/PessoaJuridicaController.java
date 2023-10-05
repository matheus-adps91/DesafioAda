package com.desafio.ada.prospect.pessoa.juridica;

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
@RequestMapping("/pessoas-juridica")
public class PessoaJuridicaController {

    private final PessoaJuridicaService pessoaJuridicaService;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ResponseConversor responseConversor;
    private final JsonConversor jsonConversor;
    private final DtoConversor dtoConversor;
    @Value("${aws.queue-name}")
    private String queueName;

    public PessoaJuridicaController(
            PessoaJuridicaService pessoaJuridicaService,
            QueueMessagingTemplate queueMessagingTemplate,
            ResponseConversor responseConversor,
            JsonConversor jsonConversor,
            DtoConversor dtoConversor)
    {
        this.pessoaJuridicaService = pessoaJuridicaService;
        this.responseConversor = responseConversor;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.jsonConversor = jsonConversor;
        this.dtoConversor = dtoConversor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaJuridicaResponse cadastrarPessoaJuridica(
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
            throws JsonProcessingException
    {
        final PessoaJuridicaDto pessoaJuridicaDto = this.dtoConversor.converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaSalva = this.pessoaJuridicaService.cadastrarPessoa(pessoaJuridicaDto);
        final String sPessoJuridicaDto = this.jsonConversor.converter(pessoaJuridicaDto);
        final Message<String> message = MessageBuilder
                .withPayload(sPessoJuridicaDto)
                .setHeader(Constantes.TIPO_DADO, Constantes.PESSOA_JURIDICA_DTO)
                .build();
        this.queueMessagingTemplate.send(queueName, message);
        final PessoaJuridicaResponse pessoaJuridicaResponse = this.responseConversor.converterParaResponse(pessoaSalva);
        return pessoaJuridicaResponse;
    }

    @GetMapping
    public List<PessoaJuridicaResponse> listarPessoasJuridicas()
    {
        final List<PessoaJuridica> pessoasJuridicas = this.pessoaJuridicaService.listarPessoasJuridicas();
        final List<PessoaJuridicaResponse> pessoasJuridicaResponse = pessoasJuridicas.stream()
                .map(pessoaJuridica -> this.responseConversor.converterParaResponse(pessoaJuridica))
                .toList();
        return pessoasJuridicaResponse;
    }

    @GetMapping("/{uuid}")
    public PessoaJuridicaResponse obterPessoaJuridicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaJuridica pessoaJuridica = this.pessoaJuridicaService.obterPessoaJuridicaPorId(uuid);
        final PessoaJuridicaResponse pessoaJuridicaResponse = this.responseConversor.converterParaResponse(pessoaJuridica);
        return pessoaJuridicaResponse;
    }

    @PutMapping("/{uuid}")
    public PessoaJuridicaResponse atualizarPessoaJuridicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
    {
        final PessoaJuridicaDto pessoaJuridicaDto = this.dtoConversor.converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaJuridicaAtualizada = this.pessoaJuridicaService.atualizarPessoaJuridicaPorId(uuid, pessoaJuridicaDto);
        final PessoaJuridicaResponse pessoaJuridicaResponse = this.responseConversor.converterParaResponse(pessoaJuridicaAtualizada);
        return pessoaJuridicaResponse;
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaJuridica(
            @PathVariable UUID uuid)
    {
        this.pessoaJuridicaService.deletarPessoaJuridica(uuid);
    }

}