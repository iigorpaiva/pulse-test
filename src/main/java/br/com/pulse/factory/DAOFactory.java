package br.com.pulse.factory;

import br.com.pulse.dao.ProdutoDAO;

public interface DAOFactory {
	ProdutoDAO getProdutoDAO();
}
