package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.exceptions.RecursoDuplicadoException;
import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.utilitarios.Constantes;
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
        final PessoaFisica pessoaFisica = converterParaEntidade(pessoaFisicaDto);
        final Optional<PessoaFisica> optPessoaFisica = this.pessoaFisicaRepository.findByUuid(pessoaFisica.getUuid());
        if (optPessoaFisica.isPresent()) {
            final String msgErro = String.format(Constantes.RECURSO_DUPLICADO, pessoaFisica.getUuid());
            throw new RecursoDuplicadoException(msgErro);
        }
        final PessoaFisica pessoaSalva = pessoaFisicaRepository.save(pessoaFisica);
        return pessoaSalva;
    }

    public List<PessoaFisica> listarPessoasFisicas() {
        final List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.findAll();
        return pessoasFisicas;
    }

    public PessoaFisica obterPessoaFisicaPorId(UUID uuid) {
        final PessoaFisica pessoaFisica = pessoaFisicaRepository.findByUuid(uuid)
            .orElseThrow( () -> {
                    final String msgErro = String.format(Constantes.RECURSO_NAO_ENCONTRADO, "física", uuid);
                    return new RecursoNaoEncontradoException(msgErro);
                }
            );
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
