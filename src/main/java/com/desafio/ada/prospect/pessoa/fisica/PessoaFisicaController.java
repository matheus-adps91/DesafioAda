package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.conversores.dto.DtoConversor;
import com.desafio.ada.prospect.conversores.json.JsonConversor;
import com.desafio.ada.prospect.conversores.response.ResponseConversor;
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
@RequestMapping("/pessoa-fisica")
public class PessoaFisicaController {

    private final PessoaFisicaService pessoaFisicaService;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ResponseConversor responseConversor;

    @Value("${aws.queue-name}")
    private String queueName;

    public PessoaFisicaController(
            PessoaFisicaService pessoaFisicaService,
            ResponseConversor responseConversor,
            QueueMessagingTemplate queueMessagingTemplate)
    {
        this.pessoaFisicaService = pessoaFisicaService;
        this.responseConversor = responseConversor;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaFisicaResponse cadastrarPessoaFisica(
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest) throws JsonProcessingException
    {
        final DtoConversor dtoConversor = new DtoConversor(new ModelMapper());
        final PessoaFisicaDto pessoaFisicaDto = dtoConversor.converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaSalva = pessoaFisicaService.cadastrarPessoa(pessoaFisicaDto);
        final String sPessoaFisicaDto = JsonConversor.converter(pessoaFisicaDto);
        final Message<String> message = MessageBuilder
                .withPayload(sPessoaFisicaDto)
                .setHeader("tipoDado", "PessoaFisicaDto")
                .build();
        queueMessagingTemplate.send(queueName, message);
        final PessoaFisicaResponse pessoaFisicaResponse = responseConversor.converterParaResponse(pessoaSalva);
        return pessoaFisicaResponse;
    }

    @GetMapping
    public List<PessoaFisicaResponse> listarPessoasFisica()
    {
        final List<PessoaFisica> pessoasFisicas = pessoaFisicaService.listarPessoasFisicas();
        final List<PessoaFisicaResponse> pessoasFisicaResponse = pessoasFisicas.stream()
                .map(pessoaFisica -> responseConversor.converterParaResponse(pessoaFisica))
                .toList();
        return pessoasFisicaResponse;
    }

    @GetMapping("{uuid}")
    public PessoaFisicaResponse obterPessoaFisicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaFisica pessoaFisica = pessoaFisicaService.obterPessoaFisicaPorId(uuid);
        final PessoaFisicaResponse pessoaFisicaResposta = responseConversor.converterParaResponse(pessoaFisica);
        return pessoaFisicaResposta;
    }

    @PutMapping("{uuid}")
    public PessoaFisicaResponse atualizarPessoaFisicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest)
    {
        final DtoConversor dtoConversor = new DtoConversor(new ModelMapper());
        final PessoaFisicaDto pessoaFisicaDto = dtoConversor.converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaFisicaAtualizada = pessoaFisicaService.atualizarPessoaFisicaPorId(uuid, pessoaFisicaDto);
        final PessoaFisicaResponse pessoaFisicaResponse = responseConversor.converterParaResponse(pessoaFisicaAtualizada);
        return pessoaFisicaResponse;
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaFisica(
            @PathVariable UUID uuid)
    {
        pessoaFisicaService.deletarPessoaFisica(uuid);
    }





}
