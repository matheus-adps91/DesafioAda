package com.desafio.ada.prospect.pessoa.fisica;

import com.desafio.ada.prospect.exceptions.RecursoDuplicadoException;
import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisica;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaDto;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaRepository;
import com.desafio.ada.prospect.pessoa.fisica.PessoaFisicaService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PessoaFisicaServiceTest {

    @Mock
    PessoaFisicaRepository pessoaFisicaRepository;
    @Mock
    ModelMapper mapper;
    @InjectMocks
    PessoaFisicaService pessoaFisicaService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    MerchantCategory merchantCategory;
    UUID uuid;
    Optional<PessoaFisica> optPessoaFisica;
    PessoaFisicaDto dto = new PessoaFisicaDto();
    PessoaFisica pessoaFisica = new PessoaFisica();

    @BeforeEach
    void resetObjects() {
        merchantCategory = MerchantCategory.valueOf("RAILROAD_FREIGHT");
        uuid = UUID.fromString("55f451d6-5cc3-11ee-8c99-0242ac120002");

        dto.setCpf("616.767.490-63");
        dto.setNome("teste");
        dto.setEmail("teste@teste.com");
        dto.setUuid(uuid);
        dto.setMerchantCategory(merchantCategory);

        pessoaFisica.setCpf("616.767.490-63");
        pessoaFisica.setNome("teste");
        pessoaFisica.setEmail("teste@teste.com");
        pessoaFisica.setUuid(uuid);
        pessoaFisica.setMerchantCategory(merchantCategory);

        optPessoaFisica = Optional.empty();
    }

    @DisplayName("Cadastrar pessoa física com sucesso")
    @Test
    void cadastrarPessoaFisicaComSucesso() {
        Mockito.when( mapper.map(dto, PessoaFisica.class)).thenReturn(pessoaFisica);
        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);
        Mockito.when(pessoaFisicaRepository.save(pessoaFisica)).thenReturn(pessoaFisica);

        PessoaFisica resultado = pessoaFisicaService.cadastrarPessoa(dto);

        Assertions.assertAll(
                "ASSERTIONS AGRUPADAS DA PESSOA FÍSICA",
                () -> Assertions.assertEquals(dto.getNome(), resultado.getNome()),
                () -> Assertions.assertEquals(dto.getCpf(), resultado.getCpf()),
                () -> Assertions.assertEquals(dto.getEmail(), resultado.getEmail()),
                () -> Assertions.assertEquals(dto.getMerchantCategory(), resultado.getMerchantCategory()),
                () -> Assertions.assertEquals(dto.getUuid(), resultado.getUuid())
        );
    }

    @DisplayName("Deve lançar exceção para pessoa física já cadastrada")
    @Test
    void lancarExcecaoParaPessoaFisicaJaCadastrada() {
        optPessoaFisica = Optional.of(pessoaFisica);

        Mockito.when( mapper.map(dto, PessoaFisica.class)).thenReturn(pessoaFisica);
        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);

        Assertions.assertThrows(RecursoDuplicadoException.class, () -> pessoaFisicaService.cadastrarPessoa(dto));
    }

    @DisplayName("Listar todas as pessosas físicas cadastradas com sucesso")
    @Test
    void listarPessoasFisicaCadastrasComSucesso() {
        List<PessoaFisica> pessoasFisica = Arrays.asList(pessoaFisica);

        Mockito.when(pessoaFisicaRepository.findAll()).thenReturn(pessoasFisica);

        var resultado = pessoaFisicaService.listarPessoasFisicas();

        Assertions.assertTrue(resultado.size() == pessoasFisica.size());
    }

    @DisplayName("Obter pessoa física por id")
    @Test
    void obterPessoaFisicaPorId() {
        optPessoaFisica = Optional.of(pessoaFisica);
        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);

        var resultado = pessoaFisicaService.obterPessoaFisicaPorId(uuid);

        Assertions.assertEquals(pessoaFisica.getUuid(), resultado.getUuid());
    }

    @DisplayName("Deve lançar exceção para pessoa física não encontrada")
    @Test
    void lancarExcecaoParaPessoaFisicaNaoEncontrada() {
        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> pessoaFisicaService.obterPessoaFisicaPorId(uuid));
    }

    @DisplayName("Atualizar pessoa física com sucesso")
    @Test
    void atualizarPessoaFisicaComSucesso() {
        optPessoaFisica = Optional.of(pessoaFisica);
        PessoaFisicaDto novoDto = new PessoaFisicaDto();
        novoDto.setNome("novo");
        novoDto.setEmail("novo@teste.com");
        novoDto.setCpf("213.784.070-75");
        novoDto.setMerchantCategory(merchantCategory);
        novoDto.setUuid(uuid);
        PessoaFisica novaPessoaFisica = new PessoaFisica();
        novaPessoaFisica.setNome("novo");
        novaPessoaFisica.setEmail("novo@teste.com");
        novaPessoaFisica.setCpf("213.784.070-75");
        novaPessoaFisica.setMerchantCategory(merchantCategory);
        novaPessoaFisica.setUuid(uuid);

        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);
        Mockito.when(pessoaFisicaRepository.saveAndFlush(any())).thenReturn(novaPessoaFisica);

        var resultado = pessoaFisicaService.atualizarPessoaFisicaPorId(uuid, novoDto);

        Assertions.assertAll(
                "ASSERTIONS AGRUPADAS DA PESSOA FÍSICA",
                () -> Assertions.assertEquals(novoDto.getNome(), resultado.getNome()),
                () -> Assertions.assertEquals(novoDto.getCpf(), resultado.getCpf()),
                () -> Assertions.assertEquals(novoDto.getEmail(), resultado.getEmail()),
                () -> Assertions.assertEquals(novoDto.getMerchantCategory(), resultado.getMerchantCategory()),
                () -> Assertions.assertEquals(novoDto.getUuid(), resultado.getUuid())
        );
    }

    @DisplayName("Deletar pessoa física com sucesso")
    @Test
    void deletarPessoaFisicaComSucesso() {
        optPessoaFisica = Optional.of(pessoaFisica);

        Mockito.when(pessoaFisicaRepository.findByUuid(uuid)).thenReturn(optPessoaFisica);

        Assertions.assertDoesNotThrow(() -> pessoaFisicaService.deletarPessoaFisica(uuid));

    }
}