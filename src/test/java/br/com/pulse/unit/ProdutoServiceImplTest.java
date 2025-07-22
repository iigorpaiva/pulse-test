package br.com.pulse.unit;

import br.com.pulse.exception.produto.ProdutoException;
import br.com.pulse.exception.produto.enums.TipoProdutoException;
import br.com.pulse.model.dto.ProdutoRequestDTO;
import br.com.pulse.factory.DAOFactory;
import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import br.com.pulse.repository.ProdutoRepository;
import br.com.pulse.service.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceImplTest {

	private static final Long PRODUTO_ID = 1L;
	private static final String PRODUTO_NOME = "Coca-Cola";
	private static final String PRODUTO_DESCRICAO = "Refrigerante de Cola";
	private static final int PRODUTO_QUANTIDADE = 50;

	private static final String EXCEPTION_MENSAGEM_INVALIDA = "O produto não pode ser cadastrado devido a informações inválidas";
	private static final String EXCEPTION_MENSAGEM_EXISTE = "O produto já existe no estoque";

	@Mock
	private DAOFactory daoFactory;

	@Mock
	private ProdutoMapper produtoMapper;

	@Mock
	private ProdutoRepository produtoRepository;

	@InjectMocks
	private ProdutoServiceImpl produtoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testInserirProdutoComSucesso() {
		ProdutoRequestDTO requestDTO = novoProdutoRequestDTO(PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		Produto produtoMockado = novoProduto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);

		when(produtoMapper.produtoRequestDtoToProduto(requestDTO)).thenReturn(produtoMockado);

		ProdutoRepository produtoRepositoryMockado = mock(ProdutoRepository.class);
		when(daoFactory.getProdutoDAO()).thenReturn(produtoRepositoryMockado);

		when(this.produtoRepository.findByNome(PRODUTO_NOME)).thenReturn(null);
		when(produtoRepositoryMockado.save(any(Produto.class))).thenReturn(produtoMockado);

		Produto produtoCriado = produtoService.inserirProduto(requestDTO);

		assertNotNull(produtoCriado);
		assertEquals(PRODUTO_NOME, produtoCriado.getNome());
		verify(produtoRepositoryMockado, times(1)).save(any(Produto.class));
	}

	@Test
	void testInserirProdutoComCamposInvalidos() {
		ProdutoRequestDTO requestDTO = novoProdutoRequestDTO(null, null, 0);

		ProdutoException exception = assertThrows(ProdutoException.class, () -> {
			produtoService.inserirProduto(requestDTO);
		});

		assertEquals(TipoProdutoException.INVALIDO, exception.getTipo());
		assertTrue(exception.getMessage().contains(EXCEPTION_MENSAGEM_INVALIDA));
	}

	@Test
	void testInserirProdutoJaExistente() {
		ProdutoRequestDTO requestDTO = novoProdutoRequestDTO(PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);

		ProdutoRepository produtoRepositoryMockado = mock(ProdutoRepository.class);
		when(daoFactory.getProdutoDAO()).thenReturn(produtoRepositoryMockado);

		Produto produtoExistente = novoProduto(PRODUTO_ID, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		when(produtoRepositoryMockado.findByNome(PRODUTO_NOME)).thenReturn(produtoExistente);

		ProdutoException exception = assertThrows(ProdutoException.class, () -> {
			produtoService.inserirProduto(requestDTO);
		});

		assertEquals(TipoProdutoException.JA_EXISTE, exception.getTipo());
		assertTrue(exception.getMessage().contains(EXCEPTION_MENSAGEM_EXISTE));
	}

	private ProdutoRequestDTO novoProdutoRequestDTO(String nome, String descricao, int quantidade) {
		ProdutoRequestDTO dto = new ProdutoRequestDTO();
		dto.setNome(nome);
		dto.setDescricao(descricao);
		dto.setQuantidade(quantidade);
		return dto;
	}

	private Produto novoProduto(Long id, String nome, String descricao, int quantidade) {
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setQuantidade(quantidade);
		return produto;
	}
}