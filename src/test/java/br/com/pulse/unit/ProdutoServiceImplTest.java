package br.com.pulse.unit;

import br.com.pulse.dao.ProdutoDAO;
import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.exception.produto.ProdutoInvalidoException;
import br.com.pulse.exception.produto.ProdutoJaExisteException;
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
		ProdutoRequestDTO dto = new ProdutoRequestDTO();
		dto.setNome(PRODUTO_NOME);
		dto.setDescricao(PRODUTO_DESCRICAO);
		dto.setQuantidade(PRODUTO_QUANTIDADE);
		
		Produto produtoMock = new Produto();
		produtoMock.setId(PRODUTO_ID);
		produtoMock.setNome(PRODUTO_NOME);
		produtoMock.setDescricao(PRODUTO_DESCRICAO);
		produtoMock.setQuantidade(PRODUTO_QUANTIDADE);
		
		when(produtoMapper.produtoRequestDtoToProduto(dto)).thenReturn(produtoMock);
		
		ProdutoDAO produtoDAO = mock(ProdutoDAO.class);
		when(daoFactory.getProdutoDAO()).thenReturn(produtoDAO);
		
		when(produtoRepository.findByNome(PRODUTO_NOME)).thenReturn(null);
		when(produtoDAO.save(any(Produto.class))).thenReturn(produtoMock);
		
		Produto produtoCriado = produtoService.inserirProduto(dto);
		
		assertNotNull(produtoCriado);
		assertEquals(PRODUTO_NOME, produtoCriado.getNome());
		verify(produtoDAO, times(1)).save(any(Produto.class));
	}

	
	@Test
	void testInserirProdutoComCamposInvalidos() {
		
		ProdutoRequestDTO dto = new ProdutoRequestDTO();
		
		ProdutoInvalidoException exception = assertThrows(ProdutoInvalidoException.class, () -> {
			produtoService.inserirProduto(dto);
		});
		
		assertTrue(exception.getMessage().contains(EXCEPTION_MENSAGEM_INVALIDA));
	}
	
	@Test
	void testInserirProdutoJaExistente() {
		ProdutoRequestDTO dto = new ProdutoRequestDTO();
		dto.setNome(PRODUTO_NOME);
		dto.setDescricao(PRODUTO_DESCRICAO);
		dto.setQuantidade(PRODUTO_QUANTIDADE);
		
		ProdutoDAO produtoDAO = mock(ProdutoDAO.class);
		when(daoFactory.getProdutoDAO()).thenReturn(produtoDAO);
		
		Produto produtoExistente = new Produto();
		produtoExistente.setId(PRODUTO_ID);
		produtoExistente.setNome(PRODUTO_NOME);
		produtoExistente.setDescricao(PRODUTO_DESCRICAO);
		produtoExistente.setQuantidade(PRODUTO_QUANTIDADE);
		when(produtoDAO.findByNome(PRODUTO_NOME)).thenReturn(produtoExistente);
		
		ProdutoJaExisteException exception = assertThrows(ProdutoJaExisteException.class, () -> {
			produtoService.inserirProduto(dto);
		});
		
		assertTrue(exception.getMessage().contains(EXCEPTION_MENSAGEM_EXISTE));
	}
}
