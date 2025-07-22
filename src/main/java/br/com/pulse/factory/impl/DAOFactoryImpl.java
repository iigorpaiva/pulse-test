package br.com.pulse.factory.impl;

import br.com.pulse.factory.DAOFactory;
import br.com.pulse.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DAOFactoryImpl implements DAOFactory {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public DAOFactoryImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public ProdutoRepository getProdutoDAO() {
        return produtoRepository;
    }
}