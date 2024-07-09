package br.com.pulse.repository;

import br.com.pulse.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Produto findByNome(String nome);
	
}
