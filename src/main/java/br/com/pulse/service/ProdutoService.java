package br.com.pulse.service;

import br.com.pulse.model.dto.ProdutoRequestDTO;
import br.com.pulse.model.dto.ProdutoResponseDTO;
import br.com.pulse.model.Produto;

import java.util.List;

public interface ProdutoService {

	/**
	 * Busca todos os produtos persistidos.
	 * @return lista de entidades Produto
	 */
	List<Produto> buscarTodosProdutos();

	/**
	 * Insere um novo produto.
	 * @param produtoRequestDTO objeto para ser persistido no banco de dados
	 * @return entidade Produto criada
	 */
	Produto inserirProduto(ProdutoRequestDTO produtoRequestDTO);

	/**
	 * Edita um produto existente.
	 * @param id identificador do produto
	 * @param produtoRequestDTO dados atualizados do produto
	 * @return entidade Produto atualizada
	 */
	Produto editarProduto(Long id, ProdutoRequestDTO produtoRequestDTO);

	/**
	 * Busca um produto pelo id.
	 * @param id identificador do produto
	 * @return entidade Produto encontrada
	 */
	Produto buscarProdutoPorId(Long id);

	/**
	 * Deleta um produto pelo id.
	 * @param id identificador do produto
	 */
	void deletarProduto(Long id);

	/**
	 * Busca todos os produtos e retorna como DTO.
	 * @return lista de ProdutoResponseDTO
	 */
	List<ProdutoResponseDTO> buscarTodosProdutosDTO();

	/**
	 * Insere um novo produto e retorna como DTO.
	 * @param produtoRequestDTO objeto para ser persistido
	 * @return ProdutoResponseDTO criado
	 */
	ProdutoResponseDTO inserirProdutoDTO(ProdutoRequestDTO produtoRequestDTO);

	/**
	 * Edita um produto e retorna como DTO.
	 * @param id identificador do produto
	 * @param produtoRequestDTO dados atualizados
	 * @return ProdutoResponseDTO atualizado
	 */
	ProdutoResponseDTO editarProdutoDTO(Long id, ProdutoRequestDTO produtoRequestDTO);

	/**
	 * Busca um produto pelo id e retorna como DTO.
	 * @param id identificador do produto
	 * @return ProdutoResponseDTO encontrado
	 */
	ProdutoResponseDTO buscarProdutoPorIdDTO(Long id);
}