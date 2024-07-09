package br.com.pulse.dao;

import br.com.pulse.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoDAO extends JpaRepository<Produto, Long> {
	
	Produto findByNome(String nome);
	
}
