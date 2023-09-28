package com.desafio.ada.prospect.pessoa.juridica;

import com.desafio.ada.prospect.exceptions.RecursoDuplicadoException;
import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.utilitarios.Constantes;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private final ModelMapper mapper;

    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository, ModelMapper mapper) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
        this.mapper = mapper;
    }

    public PessoaJuridica cadastrarPessoa(PessoaJuridicaDto pessoaJuridicaDto) {
        final PessoaJuridica pessoaJuridica = converterParaEntidade(pessoaJuridicaDto);
        final Optional<PessoaJuridica> optPessoaJuridica = pessoaJuridicaRepository.findByUuid(pessoaJuridica.getUuid());
        if (optPessoaJuridica.isPresent()) {
            final String msgErro = String.format(Constantes.RECURSO_DUPLICADO, pessoaJuridica.getUuid());
            throw new RecursoDuplicadoException(msgErro);
        }
        final PessoaJuridica pessoaSalva = pessoaJuridicaRepository.save(pessoaJuridica);
        return pessoaSalva;
    }

    public List<PessoaJuridica> listarPessoasJuridicas() {
        final List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaRepository.findAll();
        return pessoasJuridicas;
    }

    public PessoaJuridica obterPessoaJuridicaPorId(UUID uuid) {
        final PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findByUuid(uuid)
            .orElseThrow( () -> {
                    final String msgErro = String.format(Constantes.RECURSO_NAO_ENCONTRADO, "Jurídica", uuid);
                    return new RecursoNaoEncontradoException(msgErro);
                }
            );
        return pessoaJuridica;
    }

    public PessoaJuridica atualizarPessoaJuridicaPorId(UUID uuid, PessoaJuridicaDto pessoaJuridicaDto) {
        final PessoaJuridica pessoaJuridica = obterPessoaJuridicaPorId(uuid);
        final PessoaJuridica novaPessoaJuridica = new PessoaJuridica();
        novaPessoaJuridica.setId(pessoaJuridica.getId());
        novaPessoaJuridica.setUuid(pessoaJuridica.getUuid());
        novaPessoaJuridica.setCnpj(pessoaJuridicaDto.getCnpj());
        novaPessoaJuridica.setRazaoSocial(pessoaJuridicaDto.getRazaoSocial());
        novaPessoaJuridica.setCpf(pessoaJuridicaDto.getCpf());
        novaPessoaJuridica.setNome(pessoaJuridicaDto.getNome());
        novaPessoaJuridica.setEmail(pessoaJuridicaDto.getEmail());
        novaPessoaJuridica.setMerchantCategory(pessoaJuridicaDto.getMerchantCategory());
        final PessoaJuridica novaPessoaJuridicaSalva = pessoaJuridicaRepository.saveAndFlush(novaPessoaJuridica);
        return novaPessoaJuridicaSalva;
    }

    public void deletarPessoaJuridica(UUID uuid) {
        final PessoaJuridica pessoaJuridica = obterPessoaJuridicaPorId(uuid);
        pessoaJuridicaRepository.delete(pessoaJuridica);
    }

    private PessoaJuridica converterParaEntidade(PessoaJuridicaDto pessoaJuridicaDto) {
        return this.mapper.map(pessoaJuridicaDto, PessoaJuridica.class);
    }
}
