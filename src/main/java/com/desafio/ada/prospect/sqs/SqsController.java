package com.desafio.ada.prospect.sqs;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.desafio.ada.prospect.pessoa.Pessoa;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisica;
import com.desafio.ada.prospect.utilitarios.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import org.apache.commons.lang3.SerializationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sqs")
public class SqsController {

    @Value("${aws.queue-name}")
    private String queueName;
    private QueueMessagingTemplate queueMessagingTemplate;

    public SqsController(QueueMessagingTemplate messagingTemplate) {
        this.queueMessagingTemplate = messagingTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void enviarMensagemParaSqs(
            @RequestBody Object pessoaRequest)
    {
        queueMessagingTemplate.convertAndSend(queueName, pessoaRequest);
    }

    @GetMapping
    public void obterMensagemDoSqs()
    {
        var mensagemGenerica = queueMessagingTemplate.receive("spring-boot-amazon-sqs");
        String tipoDado = mensagemGenerica.getHeaders().get("tipoDado", String.class);

        if (tipoDado.equals(Constantes.PESSOA_FISICA)) {
            Object o = queueMessagingTemplate.getMessageConverter()
                    .fromMessage(mensagemGenerica, PessoaFisica.class);
            System.out.println(o);
        }

    }

}
