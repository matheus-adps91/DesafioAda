package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.conversores.atributo.MerchantCategoryConverter;
import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import jakarta.validation.Valid;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa-fisica")
public class PessoaFisicaController {

    private final PessoaFisicaService pessoaFisicaService;
    private final ModelMapper mapper;

    public PessoaFisicaController(PessoaFisicaService pessoaFisicaService, ModelMapper mapper) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaFisicaResponse cadastrarPessoaFisica(
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest)
    {
        final PessoaFisicaDto pessoaFisicaDto = converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaSalva = pessoaFisicaService.cadastrarPessoa(pessoaFisicaDto);
        final PessoaFisicaResponse pessoaFisicaResponse = converterParaResponse(pessoaSalva);
        return pessoaFisicaResponse;
    }

    @GetMapping
    public List<PessoaFisicaResponse> listarPessoasFisica()
    {
        final List<PessoaFisica> pessoasFisicas = pessoaFisicaService.listarPessoasFisicas();
        final List<PessoaFisicaResponse> pessoasFisicaResponse = pessoasFisicas.stream()
                .map(pessoaFisica -> converterParaResponse(pessoaFisica))
                .toList();
        return pessoasFisicaResponse;
    }

    @GetMapping("{uuid}")
    public PessoaFisicaResponse obterPessoaFisicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaFisica pessoaFisica = pessoaFisicaService.obterPessoaFisicaPorId(uuid);
        final PessoaFisicaResponse pessoaFisicaResposta = converterParaResponse(pessoaFisica);
        return pessoaFisicaResposta;
    }

    @PutMapping("{uuid}")
    public PessoaFisicaResponse atualizarPessoaFisicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaFisicaRequest pessoaFisicaRequest)
    {
        final PessoaFisicaDto pessoaFisicaDto = converterParaDto(pessoaFisicaRequest);
        final PessoaFisica pessoaFisicaAtualizada = pessoaFisicaService.atualizarPessoaFisicaPorId(uuid, pessoaFisicaDto);
        final PessoaFisicaResponse pessoaFisicaResponse = converterParaResponse(pessoaFisicaAtualizada);
        return pessoaFisicaResponse;
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaFisica(
            @PathVariable UUID uuid)
    {
        pessoaFisicaService.deletarPessoaFisica(uuid);
    }

    private PessoaFisicaDto converterParaDto(PessoaFisicaRequest pessoaFisicaRequest)
    {
        final Converter<String, MerchantCategory> conversor = new MerchantCategoryConverter();
        final TypeMap<PessoaFisicaRequest, PessoaFisicaDto> mapaPropriedade = this.mapper.getTypeMap(PessoaFisicaRequest.class, PessoaFisicaDto.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaFisicaRequest, PessoaFisicaDto> novoMapaPropriedade = this.mapper.createTypeMap(PessoaFisicaRequest.class, PessoaFisicaDto.class);
            novoMapaPropriedade.addMappings(
                    mapper -> mapper
                            .using(conversor)
                            .map(PessoaFisicaRequest::getCodigoCategoria, PessoaFisicaDto::setMerchantCategory)
            );
        }
        return mapper.map(pessoaFisicaRequest, PessoaFisicaDto.class);
    }

    private PessoaFisicaResponse converterParaResponse(PessoaFisica pessoaSalva)
    {
        final TypeMap<PessoaFisica, PessoaFisicaResponse> mapaPropriedade = this.mapper.getTypeMap(PessoaFisica.class, PessoaFisicaResponse.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaFisica, PessoaFisicaResponse> propertyMap = this.mapper.createTypeMap(PessoaFisica.class, PessoaFisicaResponse.class);
            propertyMap.addMapping(PessoaFisica::obterMerchantCategoryNome, PessoaFisicaResponse::setMerchantCategory);
        }
        return this.mapper.map(pessoaSalva, PessoaFisicaResponse.class);
    }

}
