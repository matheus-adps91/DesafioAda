package com.desafio.ada.prospect.pessoa.fisica;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final ModelMapper mapper;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository, ModelMapper mapper) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.mapper = mapper;
    }

    public PessoaFisica cadastrarPessoa(PessoaFisicaDto pessoaFisicaDto){
        final PessoaFisica pessoaFisica = this.converterParaEntidade(pessoaFisicaDto);
        final PessoaFisica pessoaSalva = pessoaFisicaRepository.save(pessoaFisica);
        return pessoaSalva;
    }

    public List<PessoaFisica> listarPessoasFisicas() {
        final List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.findAll();
        return pessoasFisicas;
    }

    public PessoaFisica obterPessoaFisicaPorId(UUID uuid) {
        final Optional<PessoaFisica> oPessoaFisica = pessoaFisicaRepository.findByUuid(uuid);
        final PessoaFisica pessoaFisica = oPessoaFisica.get();
        return pessoaFisica;
    }

    public PessoaFisica atualizarPessoaFisicaPorId(UUID uuid, PessoaFisicaDto pessoaFisicaDto)
    {
        final PessoaFisica pessoaFisica = obterPessoaFisicaPorId(uuid);
        final PessoaFisica novaPessoaFisica = new PessoaFisica();
        novaPessoaFisica.setId(pessoaFisica.getId());
        novaPessoaFisica.setUuid(pessoaFisica.getUuid());
        novaPessoaFisica.setCpf(pessoaFisicaDto.getCpf());
        novaPessoaFisica.setEmail(pessoaFisicaDto.getEmail());
        novaPessoaFisica.setNome(pessoaFisicaDto.getNome());
        novaPessoaFisica.setMerchantCategory(pessoaFisica.getMerchantCategory());
        final PessoaFisica novaPessoaFisicaSalva = pessoaFisicaRepository.saveAndFlush(novaPessoaFisica);
        return novaPessoaFisicaSalva;
    }

    public void deletarPessoaFisica(UUID uuid) {
        final PessoaFisica pessoaFisica = obterPessoaFisicaPorId(uuid);
        pessoaFisicaRepository.delete(pessoaFisica);
    }

    private PessoaFisica converterParaEntidade(PessoaFisicaDto pessoaFisicaDto) {
        return this.mapper.map(pessoaFisicaDto, PessoaFisica.class);
    }

}
