package br.com.pulse.service.impl;

import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.exception.produto.*;
import br.com.pulse.factory.DAOFactory;
import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import br.com.pulse.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private DAOFactory daoFactory;
	@Autowired
	private ProdutoMapper produtoMapper;
	
	@Autowired
	public ProdutoServiceImpl(DAOFactory daoFactory, ProdutoMapper produtoMapper) {
		this.daoFactory = daoFactory;
		this.produtoMapper = produtoMapper;
	}
	
	@Override
	public List<Produto> buscarTodosProdutos() {
		return daoFactory.getProdutoDAO().findAll();
	}
	
	@Override
	public Produto inserirProduto(ProdutoRequestDTO produtoRequestDTO) {
		if (validaCamposProdutoACadastrar(produtoRequestDTO)) {
			throw new ProdutoInvalidoException("O produto não pode ser cadastrado devido a informações inválidas");
		}
		
		if (isProdutoEmEstoque(produtoRequestDTO)) {
			throw new ProdutoJaExisteException("O produto já existe no estoque");
		}
		
		Produto produto = produtoMapper.produtoRequestDtoToProduto(produtoRequestDTO);
		return daoFactory.getProdutoDAO().save(produto);
	}
	
	@Override
	public Produto editarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
		Produto produtoExistente = buscarProdutoPorId(id);
		
		if (produtoExistente == null) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id);
		}
		
		if (validaCamposProdutoACadastrar(produtoRequestDTO)) {
			throw new ProdutoInvalidoException("O produto não pode ser atualizado devido a informações inválidas");
		}
		
		if (!produtoExistente.getNome().equals(produtoRequestDTO.getNome()) &&
				    isProdutoEmEstoque(produtoRequestDTO)) {
			throw new ProdutoJaExisteException("O produto já existe no estoque");
		}
		
		produtoExistente.setNome(produtoRequestDTO.getNome());
		produtoExistente.setDescricao(produtoRequestDTO.getDescricao());
		produtoExistente.setQuantidade(produtoRequestDTO.getQuantidade());
		
		return daoFactory.getProdutoDAO().save(produtoExistente);
	}
	
	@Override
	public Produto buscarProdutoPorId(Long id) {
		return daoFactory.getProdutoDAO().findById(id)
				       .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id));
	}
	
	@Override
	@Transactional
	public void deletarProduto(Long id) {
		if (!daoFactory.getProdutoDAO().existsById(id)) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado com o ID: " + id);
		}
		daoFactory.getProdutoDAO().deleteById(id);
	}
	
	private boolean isProdutoEmEstoque(ProdutoRequestDTO produtoRequestDTO) {
		return daoFactory.getProdutoDAO().findByNome(produtoRequestDTO.getNome()) != null;
	}
	
	private boolean validaCamposProdutoACadastrar(ProdutoRequestDTO produtoRequestDTO) {
		return produtoRequestDTO.getNome() == null ||
				       produtoRequestDTO.getDescricao() == null ||
				       produtoRequestDTO.getQuantidade() == null;
	}
}
