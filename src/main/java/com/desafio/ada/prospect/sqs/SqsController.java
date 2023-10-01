package com.desafio.ada.prospect.sqs;

import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaDto;
import com.desafio.ada.prospect.pessoa.juridica.PessoaJuridicaDto;
import com.desafio.ada.prospect.utilitarios.Constantes;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sqs")
public class SqsController {

    private final Logger logger = LoggerFactory.getLogger(SqsController.class);
    @Value("${aws.queue-name}")
    private String queueName;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public SqsController(QueueMessagingTemplate messagingTemplate) {
        this.queueMessagingTemplate = messagingTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void enviarMensagemParaSqs(
            @RequestBody Object pessoaRequest)
    {
        this.queueMessagingTemplate.convertAndSend(queueName, pessoaRequest);
    }

    @GetMapping
    public void obterMensagemDoSqs()
    {
        logger.info("Obtendo mensagem do serviço de fila...");
        var mensagemGenerica = queueMessagingTemplate.receive(queueName);
        if (mensagemGenerica == null) {
            logger.info(Constantes.FILA_ATENDIMENTO_VAZIA);
            throw new RecursoNaoEncontradoException(Constantes.FILA_ATENDIMENTO_VAZIA);
        }
        final String tipoDado = mensagemGenerica.getHeaders().get( Constantes.TIPO_DADO, String.class);

        if (tipoDado.equals(Constantes.PESSOA_FISICA_DTO)) {
            logger.info("Convertendo mensagem obtida da fila SQS");
            final Object oPessoa = queueMessagingTemplate.getMessageConverter()
                    .fromMessage(mensagemGenerica, PessoaFisicaDto.class);
            final PessoaFisicaDto pessoaFisicaDto = (PessoaFisicaDto) oPessoa;
            logger.info(pessoaFisicaDto.toString());
        }
        if(tipoDado.equals(Constantes.PESSOA_JURIDICA_DTO)) {
            logger.info("Convertendo mensagem obtida da fila SQS");
            final Object oPessoa = queueMessagingTemplate.getMessageConverter()
                    .fromMessage(mensagemGenerica, PessoaJuridicaDto.class);
            final PessoaJuridicaDto pessoaJuridicaDto = (PessoaJuridicaDto) oPessoa;
            logger.info(pessoaJuridicaDto.toString());
        }
    }

}
