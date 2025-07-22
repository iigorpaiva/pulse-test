package br.com.pulse.controller;

import br.com.pulse.model.dto.ProdutoRequestDTO;
import br.com.pulse.model.dto.ProdutoResponseDTO;
import br.com.pulse.model.dto.ResponseDTO;
import br.com.pulse.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	private final ProdutoService produtoService;

	public ProdutoController(@Autowired ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping("/buscarTodosProdutos")
	public ResponseEntity<ResponseDTO<List<ProdutoResponseDTO>>> buscarTodosProdutos() {
		List<ProdutoResponseDTO> produtos = produtoService.buscarTodosProdutosDTO();
		String msg = produtos.isEmpty() ? "Nenhum produto encontrado" : "Produtos encontrados com sucesso";
		return ResponseEntity.ok(
				ResponseDTO.<List<ProdutoResponseDTO>>builder()
						.data(produtos)
						.status(HttpStatus.OK)
						.msg(msg)
						.build()
		);
	}

	@PostMapping("/inserirProduto")
	public ResponseEntity<ResponseDTO<ProdutoResponseDTO>> inserirProduto(@RequestBody ProdutoRequestDTO dto) {
		ProdutoResponseDTO produto = produtoService.inserirProdutoDTO(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ResponseDTO.<ProdutoResponseDTO>builder()
						.data(produto)
						.status(HttpStatus.CREATED)
						.msg("Produto criado com sucesso")
						.build()
				);
	}

	@PutMapping("/editarProduto/{id}")
	public ResponseEntity<ResponseDTO<ProdutoResponseDTO>> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO dto) {
		ProdutoResponseDTO produto = produtoService.editarProdutoDTO(id, dto);
		return ResponseEntity.ok(
				ResponseDTO.<ProdutoResponseDTO>builder()
						.data(produto)
						.status(HttpStatus.OK)
						.msg("Produto atualizado com sucesso")
						.build()
		);
	}

	@DeleteMapping("/deletarProduto/{id}")
	public ResponseEntity<ResponseDTO<Void>> deletarProduto(@PathVariable Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(ResponseDTO.<Void>builder()
						.data(null)
						.status(HttpStatus.NO_CONTENT)
						.msg("Produto deletado com sucesso")
						.build()
				);
	}

	@GetMapping("/buscarProduto/{id}")
	public ResponseEntity<ResponseDTO<ProdutoResponseDTO>> buscarProdutoPorId(@PathVariable Long id) {
		ProdutoResponseDTO produto = produtoService.buscarProdutoPorIdDTO(id);
		return ResponseEntity.ok(
				ResponseDTO.<ProdutoResponseDTO>builder()
						.data(produto)
						.status(HttpStatus.OK)
						.msg("Produto encontrado com sucesso")
						.build()
		);
	}
}