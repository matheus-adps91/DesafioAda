package com.desafio.ada.prospect.pessoa.juridica;

import com.desafio.ada.prospect.conversores.dto.DtoConversor;
import com.desafio.ada.prospect.conversores.json.JsonConversor;
import com.desafio.ada.prospect.conversores.response.ResponseConversor;
import com.desafio.ada.prospect.utilitarios.Constantes;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    @Value("${aws.queue-name}")
    private String queueName;

    public PessoaJuridicaController(
            PessoaJuridicaService pessoaJuridicaService,
            QueueMessagingTemplate queueMessagingTemplate,
            ResponseConversor responseConversor)
    {
        this.pessoaJuridicaService = pessoaJuridicaService;
        this.responseConversor = responseConversor;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaJuridicaResponse cadastrarPessoaJuridica(
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
            throws JsonProcessingException
    {
        final DtoConversor dtoConversor = new DtoConversor(new ModelMapper());
        final PessoaJuridicaDto pessoaJuridicaDto = dtoConversor.converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaSalva = pessoaJuridicaService.cadastrarPessoa(pessoaJuridicaDto);
        final String sPessoJuridicaDto = JsonConversor.converter(pessoaJuridicaDto);
        final Message<String> message = MessageBuilder
                .withPayload(sPessoJuridicaDto)
                .setHeader(Constantes.TIPO_DADO, Constantes.PESSOA_JURIDICA_DTO)
                .build();
        queueMessagingTemplate.send(queueName, message);
        final PessoaJuridicaResponse pessoaJuridicaResponse = responseConversor.converterParaResponse(pessoaSalva);
        return pessoaJuridicaResponse;
    }

    @GetMapping
    public List<PessoaJuridicaResponse> listarPessoasJuridicas()
    {
        final List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaService.listarPessoasJuridicas();
        final List<PessoaJuridicaResponse> pessoasJuridicaResponse = pessoasJuridicas.stream()
                .map(pessoaJuridica -> responseConversor.converterParaResponse(pessoaJuridica))
                .toList();
        return pessoasJuridicaResponse;
    }

    @GetMapping("/{uuid}")
    public PessoaJuridicaResponse obterPessoaJuridicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaJuridica pessoaJuridica = pessoaJuridicaService.obterPessoaJuridicaPorId(uuid);
        final PessoaJuridicaResponse pessoaJuridicaResponse = responseConversor.converterParaResponse(pessoaJuridica);
        return pessoaJuridicaResponse;
    }

    @PutMapping("/{uuid}")
    public PessoaJuridicaResponse atualizarPessoaJuridicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
    {
        final DtoConversor dtoConversor = new DtoConversor(new ModelMapper());
        final PessoaJuridicaDto pessoaJuridicaDto = dtoConversor.converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaJuridicaAtualizada = pessoaJuridicaService.atualizarPessoaJuridicaPorId(uuid, pessoaJuridicaDto);
        final PessoaJuridicaResponse pessoaJuridicaResponse = responseConversor.converterParaResponse(pessoaJuridicaAtualizada);
        return pessoaJuridicaResponse;
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaJuridica(
            @PathVariable UUID uuid)
    {
        pessoaJuridicaService.deletarPessoaJuridica(uuid);
    }

}