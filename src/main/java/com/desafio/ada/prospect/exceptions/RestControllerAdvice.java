package com.desafio.ada.prospect.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RecursoDuplicadoException.class, RecursoNaoEncontradoException.class})
    public ResponseEntity<ErroResponse> handleMethodArgumentNotValid(
            Exception ex)
    {
        final ErroResponse erroResponse = new ErroResponse();
        erroResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        erroResponse.setDataHoraFalha(LocalDateTime.now());
        erroResponse.setMensagensErro(Arrays.asList(ex.getMessage()));
        erroResponse.setNomeClasse(ex.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        final List<String> erros = ex.getBindingResult().getAllErrors().stream()
                .map( obj -> obj.getDefaultMessage())
                .toList();
        final ErroResponse  erroResponse = new ErroResponse();
        erroResponse.setDataHoraFalha(LocalDateTime.now());
        erroResponse.setNomeClasse(ex.getClass().getName());
        erroResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        erroResponse.setMensagensErro(erros);
        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }

}
