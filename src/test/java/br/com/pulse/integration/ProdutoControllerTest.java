package br.com.pulse.integration;

import br.com.pulse.controller.ProdutoController;
import br.com.pulse.model.dto.ProdutoResponseDTO;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProdutoControllerTest {

	private static final String PRODUTO_NOME_COCA_COLA = "Coca-Cola";
	private static final String PRODUTO_NOME_PEPSI = "Coca-Cola";
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
		ProdutoResponseDTO coca = criarProdutoResponseDTO(1L, PRODUTO_NOME_COCA_COLA, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);
		ProdutoResponseDTO pepsi = criarProdutoResponseDTO(2L, PRODUTO_NOME_PEPSI, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);

		List<ProdutoResponseDTO> produtos = Arrays.asList(coca, pepsi);

		when(produtoService.buscarTodosProdutosDTO()).thenReturn(produtos);

		mockMvc.perform(get("/api/produto/buscarTodosProdutos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(2)))
				.andExpect(jsonPath("$.data[0].nome", is(PRODUTO_NOME_COCA_COLA)))
				.andExpect(jsonPath("$.data[1].nome", is(PRODUTO_NOME_PEPSI)));
	}

	@Test
	void testInserirProduto() throws Exception {
		ProdutoResponseDTO produtoResponseDTO = criarProdutoResponseDTO(1L, PRODUTO_NOME_COCA_COLA, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);

		when(produtoService.inserirProdutoDTO(any())).thenReturn(produtoResponseDTO);

		mockMvc.perform(post("/api/produto/inserirProduto")
						.contentType(MediaType.APPLICATION_JSON)
						.content(PRODUTO_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.nome", is(PRODUTO_NOME_COCA_COLA)))
				.andExpect(jsonPath("$.data.descricao", is(PRODUTO_DESCRICAO)))
				.andExpect(jsonPath("$.data.quantidade", is(PRODUTO_QUANTIDADE)));
	}

	@Test
	void testEditarProduto() throws Exception {
		ProdutoResponseDTO produtoResponseDTO = criarProdutoResponseDTO(1L, PRODUTO_ATUALIZADO_NOME, PRODUTO_ATUALIZADO_DESCRICAO, PRODUTO_ATUALIZADO_QUANTIDADE);

		when(produtoService.editarProdutoDTO(anyLong(), any())).thenReturn(produtoResponseDTO);

		mockMvc.perform(put("/api/produto/editarProduto/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(PRODUTO_ATUALIZADO_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome", is(PRODUTO_ATUALIZADO_NOME)))
				.andExpect(jsonPath("$.data.descricao", is(PRODUTO_ATUALIZADO_DESCRICAO)))
				.andExpect(jsonPath("$.data.quantidade", is(PRODUTO_ATUALIZADO_QUANTIDADE)));
	}

	@Test
	void testDeletarProduto() throws Exception {
		doNothing().when(produtoService).deletarProduto(anyLong());

		mockMvc.perform(delete("/api/produto/deletarProduto/{id}", 1L))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	void testBuscarProdutoPorId() throws Exception {
		ProdutoResponseDTO produtoResponseDTO = criarProdutoResponseDTO(1L, PRODUTO_NOME_COCA_COLA, PRODUTO_DESCRICAO, PRODUTO_QUANTIDADE);

		when(produtoService.buscarProdutoPorIdDTO(1L)).thenReturn(produtoResponseDTO);

		mockMvc.perform(get("/api/produto/buscarProduto/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome", is(PRODUTO_NOME_COCA_COLA)))
				.andExpect(jsonPath("$.data.descricao", is(PRODUTO_DESCRICAO)))
				.andExpect(jsonPath("$.data.quantidade", is(PRODUTO_QUANTIDADE)));
	}

	@Test
	void testBuscarTodosProdutosRetornaMensagemNenhumProdutoEncontrado() throws Exception {
		when(produtoService.buscarTodosProdutosDTO()).thenReturn(List.of());

		mockMvc.perform(get("/api/produto/buscarTodosProdutos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(0)))
				.andExpect(jsonPath("$.msg", is("Nenhum produto encontrado")));
	}

	private ProdutoResponseDTO criarProdutoResponseDTO(Long id, String nome, String descricao, int quantidade) {
		ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO();
		produtoResponseDTO.setId(id);
		produtoResponseDTO.setNome(nome);
		produtoResponseDTO.setDescricao(descricao);
		produtoResponseDTO.setQuantidade(quantidade);
		return produtoResponseDTO;
	}
}