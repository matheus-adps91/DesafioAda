package com.desafio.ada.prospect.pessoa.juridica;

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
@RequestMapping("/pessoas-juridica")
public class PessoaJuridicaController {

    private PessoaJuridicaService pessoaJuridicaService;

    private ModelMapper mapper;
    public PessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService, ModelMapper mapper) {
        this.pessoaJuridicaService = pessoaJuridicaService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaJuridicaResponse cadastrarPessoaJuridica(
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
    {
        final PessoaJuridicaDto pessoaJuridicaDto = converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaSalva = pessoaJuridicaService.cadastrarPessoa(pessoaJuridicaDto);
        final PessoaJuridicaResponse pessoaJuridicaResponse = converterParaResponse(pessoaSalva);
        return pessoaJuridicaResponse;
    }

    @GetMapping
    public List<PessoaJuridicaResponse> listarPessoasJuridicas()
    {
        final List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaService.listarPessoasJuridicas();
        final List<PessoaJuridicaResponse> pessoasJuridicaResponse = pessoasJuridicas.stream()
                .map(pessoaJuridica -> converterParaResponse(pessoaJuridica))
                .toList();
        return pessoasJuridicaResponse;
    }

    @GetMapping("/{uuid}")
    public PessoaJuridicaResponse obterPessoaJuridicaPorId(
            @PathVariable UUID uuid)
    {
        final PessoaJuridica pessoaJuridica = pessoaJuridicaService.obterPessoaJuridicaPorId(uuid);
        final PessoaJuridicaResponse pessoaJuridicaResponse = converterParaResponse(pessoaJuridica);
        return pessoaJuridicaResponse;
    }

    @PutMapping("/{uuid}")
    public PessoaJuridicaResponse atualizarPessoaJuridicaPorId(
            @PathVariable UUID uuid,
            @Valid @RequestBody PessoaJuridicaRequest pessoaJuridicaRequest)
    {
        final PessoaJuridicaDto pessoaJuridicaDto = converterParaDto(pessoaJuridicaRequest);
        final PessoaJuridica pessoaJuridicaAtualizada = pessoaJuridicaService.atualizarPessoaJuridicaPorId(uuid, pessoaJuridicaDto);
        final PessoaJuridicaResponse pessoaJuridicaResponse = converterParaResponse(pessoaJuridicaAtualizada);
        return pessoaJuridicaResponse;
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPessoaJuridica(
            @PathVariable UUID uuid)
    {
        pessoaJuridicaService.deletarPessoaJuridica(uuid);
    }

    private PessoaJuridicaDto converterParaDto(PessoaJuridicaRequest pessoaJuridicaRequest) {
        final Converter<String, MerchantCategory> conversor = new MerchantCategoryConverter();
        final TypeMap<PessoaJuridicaRequest, PessoaJuridicaDto> mapaPropriedade = this.mapper.getTypeMap(PessoaJuridicaRequest.class, PessoaJuridicaDto.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaJuridicaRequest, PessoaJuridicaDto> novoMapaPropriedade = this.mapper.createTypeMap(PessoaJuridicaRequest.class, PessoaJuridicaDto.class);
            novoMapaPropriedade.addMappings(
                    mapper -> mapper
                            .using(conversor)
                            .map(PessoaJuridicaRequest::getCodigoCategoria, PessoaJuridicaDto::setMerchantCategory)
            );
        }
        return mapper.map(pessoaJuridicaRequest, PessoaJuridicaDto.class);
    }

    private PessoaJuridicaResponse converterParaResponse(PessoaJuridica pessoaSalva) {
        final TypeMap<PessoaJuridica, PessoaJuridicaResponse> mapaPropriedade = this.mapper.getTypeMap(PessoaJuridica.class, PessoaJuridicaResponse.class);
        if (mapaPropriedade == null) {
            TypeMap<PessoaJuridica, PessoaJuridicaResponse> novoMapaPropriedade = this.mapper.createTypeMap(PessoaJuridica.class, PessoaJuridicaResponse.class);
            novoMapaPropriedade.addMapping(PessoaJuridica::obterMerchantCategoryNome, PessoaJuridicaResponse::setMerchantCategory);
        }
        return this.mapper.map(pessoaSalva, PessoaJuridicaResponse.class);
    }

}
