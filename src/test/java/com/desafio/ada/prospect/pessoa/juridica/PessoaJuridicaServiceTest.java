package com.desafio.ada.prospect.pessoa.juridica;

import com.desafio.ada.prospect.exceptions.RecursoDuplicadoException;
import com.desafio.ada.prospect.exceptions.RecursoNaoEncontradoException;
import com.desafio.ada.prospect.pessoa.enums.MerchantCategory;
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
public class PessoaJuridicaServiceTest {

    @Mock
    PessoaJuridicaRepository pessoaJuridicaRepository;
    @Mock
    ModelMapper mapper;
    @InjectMocks
    PessoaJuridicaService pessoaJuridicaService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    MerchantCategory merchantCategory;
    UUID uuid;
    Optional<PessoaJuridica>optPessoaJuridica;
    PessoaJuridicaDto dto = new PessoaJuridicaDto();
    PessoaJuridica pessoaJuridica = new PessoaJuridica();

    @BeforeEach
    void resetObjects() {
        merchantCategory = MerchantCategory.valueOf("RAILROAD_FREIGHT");
        uuid = UUID.fromString("55f451d6-5cc3-11ee-8c99-0242ac120002");

        dto.setCnpj("93.946.517/0001-59");
        dto.setRazaoSocial("teste software ltda");
        dto.setCpf("213.784.070-75");
        dto.setNome("teste");
        dto.setEmail("teste@teste.com");
        dto.setUuid(uuid);
        dto.setMerchantCategory(merchantCategory);

        pessoaJuridica.setCnpj("93.946.517/0001-59");
        pessoaJuridica.setRazaoSocial("teste software ltda");
        pessoaJuridica.setCpf("213.784.070-75");
        pessoaJuridica.setNome("teste");
        pessoaJuridica.setEmail("teste@teste.com");
        pessoaJuridica.setUuid(uuid);
        pessoaJuridica.setMerchantCategory(merchantCategory);

        optPessoaJuridica = Optional.empty();
    }

    @DisplayName("Cadastrar pessoa jurídica com sucesso")
    @Test
    void cadastrarPessoaJuridicaComSucesso() {
        Mockito.when( mapper.map(dto, PessoaJuridica.class)).thenReturn(pessoaJuridica);
        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);
        Mockito.when(pessoaJuridicaRepository.save(pessoaJuridica)).thenReturn(pessoaJuridica);

        PessoaJuridica resultado = pessoaJuridicaService.cadastrarPessoa(dto);

        Assertions.assertAll(
                "ASSERTIONS AGRUPADAS DA PESSOA JURÍDICA",
                () -> Assertions.assertEquals(dto.getCnpj(), resultado.getCnpj()),
                () -> Assertions.assertEquals(dto.getRazaoSocial(), resultado.getRazaoSocial()),
                () -> Assertions.assertEquals(dto.getNome(), resultado.getNome()),
                () -> Assertions.assertEquals(dto.getCpf(), resultado.getCpf()),
                () -> Assertions.assertEquals(dto.getEmail(), resultado.getEmail()),
                () -> Assertions.assertEquals(dto.getMerchantCategory(), resultado.getMerchantCategory()),
                () -> Assertions.assertEquals(dto.getUuid(), resultado.getUuid())
        );
    }

    @DisplayName("Deve lançar exceção para pessoa jurídica já cadastrada")
    @Test
    void lancarExcecaoParaPessoaJuridicaJaCadastrada() {
        optPessoaJuridica = Optional.of(pessoaJuridica);

        Mockito.when( mapper.map(dto, PessoaJuridica.class)).thenReturn(pessoaJuridica);
        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);

        Assertions.assertThrows(RecursoDuplicadoException.class, () -> pessoaJuridicaService.cadastrarPessoa(dto));
    }

    @DisplayName("Listar todas as pessosas jurídicas cadastradas com sucesso")
    @Test
    void listarPessoasJuridicaCadastrasComSucesso() {
        List<PessoaJuridica> pessoasJuridica = Arrays.asList(pessoaJuridica);

        Mockito.when(pessoaJuridicaRepository.findAll()).thenReturn(pessoasJuridica);

        var resultado = pessoaJuridicaService.listarPessoasJuridicas();

        Assertions.assertTrue(resultado.size() == pessoasJuridica.size());
    }

    @DisplayName("Obter pessoa juridica por id")
    @Test
    void obterPessoaJuridicaPorId() {
        optPessoaJuridica = Optional.of(pessoaJuridica);
        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);

        var resultado = pessoaJuridicaService.obterPessoaJuridicaPorId(uuid);

        Assertions.assertEquals(pessoaJuridica.getUuid(), resultado.getUuid());
    }

    @DisplayName("Deve lançar exceção para pessoa jurídica não encontrada")
    @Test
    void lancarExcecaoParaPessoaJuridicaNaoEncontrada() {
        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> pessoaJuridicaService.obterPessoaJuridicaPorId(uuid));
    }

    @DisplayName("Atualizar pessoa jurídica com sucesso")
    @Test
    void atualizarPessoaJuridicaComSucesso() {
        optPessoaJuridica = Optional.of(pessoaJuridica);
        PessoaJuridicaDto novoDto = new PessoaJuridicaDto();
        novoDto.setCnpj("34.213.668/0001-39");
        novoDto.setRazaoSocial("nova software house");
        novoDto.setNome("novo");
        novoDto.setEmail("novo@teste.com");
        novoDto.setCpf("175.560.710-57");
        novoDto.setMerchantCategory(merchantCategory);
        novoDto.setUuid(uuid);
        PessoaJuridica novaPessoaJuridica = new PessoaJuridica();
        novaPessoaJuridica.setCnpj("34.213.668/0001-39");
        novaPessoaJuridica.setRazaoSocial("nova software house");
        novaPessoaJuridica.setNome("novo");
        novaPessoaJuridica.setEmail("novo@teste.com");
        novaPessoaJuridica.setCpf("175.560.710-57");
        novaPessoaJuridica.setMerchantCategory(merchantCategory);
        novaPessoaJuridica.setUuid(uuid);

        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);
        Mockito.when(pessoaJuridicaRepository.saveAndFlush(any())).thenReturn(novaPessoaJuridica);

        var resultado = pessoaJuridicaService.atualizarPessoaJuridicaPorId(uuid, novoDto);

        Assertions.assertAll(
                "ASSERTIONS AGRUPADAS DA PESSOA JURÍDICA",
                () -> Assertions.assertEquals(novoDto.getCnpj(), resultado.getCnpj()),
                () -> Assertions.assertEquals(novoDto.getRazaoSocial(), resultado.getRazaoSocial()),
                () -> Assertions.assertEquals(novoDto.getNome(), resultado.getNome()),
                () -> Assertions.assertEquals(novoDto.getCpf(), resultado.getCpf()),
                () -> Assertions.assertEquals(novoDto.getEmail(), resultado.getEmail()),
                () -> Assertions.assertEquals(novoDto.getMerchantCategory(), resultado.getMerchantCategory()),
                () -> Assertions.assertEquals(novoDto.getUuid(), resultado.getUuid())
        );
    }

    @DisplayName("Deletar pessoa jurídica com sucesso")
    @Test
    void deletarPessoaFisicaComSucesso() {
        optPessoaJuridica = Optional.of(pessoaJuridica);

        Mockito.when(pessoaJuridicaRepository.findByUuid(uuid)).thenReturn(optPessoaJuridica);

        Assertions.assertDoesNotThrow(() -> pessoaJuridicaService.deletarPessoaJuridica(uuid));

    }
}
