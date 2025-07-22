package br.com.pulse.factory;

import br.com.pulse.repository.ProdutoRepository;

public interface DAOFactory {
	ProdutoRepository getProdutoDAO();
}
