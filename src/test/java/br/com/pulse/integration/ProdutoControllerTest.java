package br.com.pulse.integration;

import br.com.pulse.controller.ProdutoController;
import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.model.Produto;
import br.com.pulse.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProdutoControllerTest {
	
	private static final String PRODUTO_NOME = "Coca-Cola";
	private static final String PRODUTO_DESCRICAO = "Refrigerante de Cola";
	private static final int PRODUTO_QUANTIDADE = 50;
	private static final String PRODUTO_ATUALIZADO_NOME = "Coca-Cola Zero";
	private static final String PRODUTO_ATUALIZADO_DESCRICAO = "Refrigerante de Cola sem Açúcar";
	private static final int PRODUTO_ATUALIZADO_QUANTIDADE = 100;
	private static final String PRODUTO_JSON = "{\"nome\":\"Coca-Cola\", \"descricao\":\"Refrigerante de Cola\", \"quantidade\":50}";
	private static final String PRODUTO_ATUALIZADO_JSON = "{\"nome\":\"Coca-Cola Zero\", \"descricao\":\"Refrigerante de Cola sem Açúcar\", \"quantidade\":100}";
	
	@Mock
	private ProdutoService produtoService;
	
	@InjectMocks
	private ProdutoController produtoController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
	}
	
	@Test
	void testBuscarTodosProdutos() throws Exception {
		Produto produto1 = criarProduto(1L, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		Produto produto2 = criarProduto(2L, "Pepsi", "Refrigerante de Cola", 20);
		
		List<Produto> produtos = Arrays.asList(produto1, produto2);
		
		when(produtoService.buscarTodosProdutos()).thenReturn(produtos);
		
		mockMvc.perform(get("/api/produto/buscarTodosProdutos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].nome", is(PRODUTO_NOME)))
				.andExpect(jsonPath("$[1].nome", is("Pepsi")));
	}
	
	@Test
	void testInserirProduto() throws Exception {
		ProdutoRequestDTO dto = criarProdutoRequestDTO(PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		Produto produto = criarProduto(1L, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		
		when(produtoService.inserirProduto(any())).thenReturn(produto);
		
		mockMvc.perform(post("/api/produto/inserirProduto")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(PRODUTO_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", is(PRODUTO_NOME)))
				.andExpect(jsonPath("$.descricao", is(PRODUTO_DESCRICAO)))
				.andExpect(jsonPath("$.quantidade", is(PRODUTO_QUANTIDADE)));
	}
	
	@Test
	void testEditarProduto() throws Exception {
		ProdutoRequestDTO dto = criarProdutoRequestDTO(PRODUTO_ATUALIZADO_NOME, PRODUTO_ATUALIZADO_DESCRICAO, PRODUTO_ATUALIZADO_QUANTIDADE);
		Produto produto = criarProduto(1L, PRODUTO_ATUALIZADO_NOME, PRODUTO_ATUALIZADO_DESCRICAO, PRODUTO_ATUALIZADO_QUANTIDADE);
		
		when(produtoService.editarProduto(anyLong(), any())).thenReturn(produto);
		
		mockMvc.perform(put("/api/produto/editarProduto/{id}", 1L)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(PRODUTO_ATUALIZADO_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is(PRODUTO_ATUALIZADO_NOME)))
				.andExpect(jsonPath("$.descricao", is(PRODUTO_ATUALIZADO_DESCRICAO)))
				.andExpect(jsonPath("$.quantidade", is(PRODUTO_ATUALIZADO_QUANTIDADE)));
	}
	
	@Test
	void testDeletarProduto() throws Exception {
		mockMvc.perform(delete("/api/produto/deletarProduto/{id}", 1L))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void testBuscarProdutoPorId() throws Exception {
		Produto produto = criarProduto(1L, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		
		when(produtoService.buscarProdutoPorId(1L)).thenReturn(produto);
		
		mockMvc.perform(get("/api/produto/buscarProduto/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is(PRODUTO_NOME)))
				.andExpect(jsonPath("$.descricao", is(PRODUTO_DESCRICAO)))
				.andExpect(jsonPath("$.quantidade", is(PRODUTO_QUANTIDADE)));
	}
	
	private ProdutoRequestDTO criarProdutoRequestDTO(String nome, String descricao, int quantidade) {
		ProdutoRequestDTO dto = new ProdutoRequestDTO();
		dto.setNome(nome);
		dto.setDescricao(descricao);
		dto.setQuantidade(quantidade);
		return dto;
	}
	
	private Produto criarProduto(Long id, String nome, String descricao, int quantidade) {
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setQuantidade(quantidade);
		return produto;
	}
}
