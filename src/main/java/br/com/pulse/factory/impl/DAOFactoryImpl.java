package br.com.pulse.factory.impl;

import br.com.pulse.dao.ProdutoDAO;
import br.com.pulse.factory.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DAOFactoryImpl implements DAOFactory {
	
	private final ProdutoDAO produtoDAO;
	
	@Autowired
	public DAOFactoryImpl(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}
	
	@Override
	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}
}