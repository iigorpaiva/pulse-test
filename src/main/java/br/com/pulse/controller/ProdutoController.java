package br.com.pulse.controller;

import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.dto.ProdutoResponseDTO;
import br.com.pulse.model.Produto;
import br.com.pulse.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping("/buscarTodosProdutos")
	public ResponseEntity<List<ProdutoResponseDTO>> buscarTodosProdutos() {
		List<Produto> listaTodosProdutos = produtoService.buscarTodosProdutos();
		List<ProdutoResponseDTO> responseDTOs = listaTodosProdutos.stream()
				                                        .map(this::convertToResponseDTO)
				                                        .collect(Collectors.toList());
		return ResponseEntity.ok(responseDTOs);
	}
	
	@PostMapping("/inserirProduto")
	public ResponseEntity<ProdutoResponseDTO> inserirProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
		Produto produtoCriado = produtoService.inserirProduto(produtoRequestDTO);
		ProdutoResponseDTO responseDTO = convertToResponseDTO(produtoCriado);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
	@PutMapping("/editarProduto/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
		Produto produtoAtualizado = produtoService.editarProduto(id, produtoRequestDTO);
		ProdutoResponseDTO responseDTO = convertToResponseDTO(produtoAtualizado);
		return ResponseEntity.ok(responseDTO);
	}
	
	@DeleteMapping("/deletarProduto/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/buscarProduto/{id}")
	public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Long id) {
		Produto produto = produtoService.buscarProdutoPorId(id);
		ProdutoResponseDTO responseDTO = convertToResponseDTO(produto);
		return ResponseEntity.ok(responseDTO);
	}
	
	private ProdutoResponseDTO convertToResponseDTO(Produto produto) {
		return ProdutoResponseDTO.builder()
				       .id(produto.getId())
				       .nome(produto.getNome())
				       .descricao(produto.getDescricao())
				       .quantidade(produto.getQuantidade())
				       .build();
	}
}
