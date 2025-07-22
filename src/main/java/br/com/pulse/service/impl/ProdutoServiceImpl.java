package br.com.pulse.service.impl;

import br.com.pulse.exception.produto.ProdutoException;
import br.com.pulse.exception.produto.enums.TipoProdutoException;
import br.com.pulse.factory.DAOFactory;
import br.com.pulse.mapper.ProdutoMapper;
import br.com.pulse.model.Produto;
import br.com.pulse.model.dto.ProdutoRequestDTO;
import br.com.pulse.model.dto.ProdutoResponseDTO;
import br.com.pulse.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private static final String MSG_INVALIDO_CADASTRO = "O produto não pode ser cadastrado devido a informações inválidas";
	private static final String MSG_JA_EXISTE = "O produto já existe no estoque";
	private static final String MSG_NAO_ENCONTRADO = "Produto não encontrado com o ID: ";

	private final DAOFactory daoFactory;
	private final ProdutoMapper produtoMapper;

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
			throw new ProdutoException(TipoProdutoException.INVALIDO, MSG_INVALIDO_CADASTRO);
		}

		if (isProdutoEmEstoque(produtoRequestDTO)) {
			throw new ProdutoException(TipoProdutoException.JA_EXISTE, MSG_JA_EXISTE);
		}

		Produto produto = produtoMapper.produtoRequestDtoToProduto(produtoRequestDTO);
		return daoFactory.getProdutoDAO().save(produto);
	}

	@Override
	public Produto editarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
		Produto produtoExistente = buscarProdutoPorId(id);

		if (produtoExistente == null) {
			throw new ProdutoException(TipoProdutoException.NAO_ENCONTRADO, MSG_NAO_ENCONTRADO + id);
		}

		if (validaCamposProdutoACadastrar(produtoRequestDTO)) {
			throw new ProdutoException(TipoProdutoException.INVALIDO, MSG_INVALIDO_CADASTRO);
		}

		if (!produtoExistente.getNome().equals(produtoRequestDTO.getNome()) &&
				isProdutoEmEstoque(produtoRequestDTO)) {
			throw new ProdutoException(TipoProdutoException.JA_EXISTE, MSG_JA_EXISTE);
		}

		produtoExistente.setNome(produtoRequestDTO.getNome());
		produtoExistente.setDescricao(produtoRequestDTO.getDescricao());
		produtoExistente.setQuantidade(produtoRequestDTO.getQuantidade());

		return daoFactory.getProdutoDAO().save(produtoExistente);
	}

	@Override
	public Produto buscarProdutoPorId(Long id) {
		return daoFactory.getProdutoDAO().findById(id)
				.orElseThrow(() -> new ProdutoException(TipoProdutoException.NAO_ENCONTRADO, MSG_NAO_ENCONTRADO + id));
	}

	@Override
	@Transactional
	public void deletarProduto(Long id) {
		if (!daoFactory.getProdutoDAO().existsById(id)) {
			throw new ProdutoException(TipoProdutoException.NAO_ENCONTRADO, MSG_NAO_ENCONTRADO + id);
		}
		daoFactory.getProdutoDAO().deleteById(id);
	}

	@Override
	public List<ProdutoResponseDTO> buscarTodosProdutosDTO() {
		return buscarTodosProdutos().stream()
				.map(produtoMapper::produtoToProdutoResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public ProdutoResponseDTO inserirProdutoDTO(ProdutoRequestDTO produtoRequestDTO) {
		Produto produto = inserirProduto(produtoRequestDTO);
		return produtoMapper.produtoToProdutoResponseDto(produto);
	}

	@Override
	public ProdutoResponseDTO editarProdutoDTO(Long id, ProdutoRequestDTO produtoRequestDTO) {
		Produto produto = editarProduto(id, produtoRequestDTO);
		return produtoMapper.produtoToProdutoResponseDto(produto);
	}

	@Override
	public ProdutoResponseDTO buscarProdutoPorIdDTO(Long id) {
		Produto produto = buscarProdutoPorId(id);
		return produtoMapper.produtoToProdutoResponseDto(produto);
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