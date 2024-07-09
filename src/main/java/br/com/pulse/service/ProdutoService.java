package br.com.pulse.service;

import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.model.Produto;

import java.util.List;

public interface ProdutoService {
	
	List<Produto> buscarTodosProdutos();
	
	/**
	 * Método para inserir um produto
	 * @param produtoRequestDTO objeto para ser persistido no banco de dados
	 * @return Produto
	 */
	Produto inserirProduto(ProdutoRequestDTO produtoRequestDTO);
	
	
	/**
	 * Método para editar o produto
	 * @param id parâmetro usado para encontrar o produto que já foi persistido
	 * @param produtoRequestDTO objeto que passará os novos dados do produto encontradoa
	 * @return Produto
	 */
	Produto editarProduto(Long id, ProdutoRequestDTO produtoRequestDTO);
	
	
	/**
	 * Método para buscar um produto pelo id
	 * @param id parâmetro passado para encontrar um produto já persistido
	 * @return Produto
	 */
	Produto buscarProdutoPorId(Long id);
	
	
	/**
	 * Método para deletar um produto já persistido
	 * @param id parâmetro usado para encontrar o produto já persistido
	 */
	void deletarProduto(Long id);
}
