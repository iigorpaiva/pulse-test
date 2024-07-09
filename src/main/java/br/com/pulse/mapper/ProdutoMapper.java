package br.com.pulse.mapper;

import br.com.pulse.dto.ProdutoRequestDTO;
import br.com.pulse.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {
	
	/**
	 * Representa a instância da classe
	 */
	ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
	
	/**
	 *
	 * @param produtoRequestDTO o objeto a ser convertido
	 * @return Produto o objeto de destino convertido
	 */
	@Mapping(target = "id", ignore = true)
	Produto produtoRequestDtoToProduto(ProdutoRequestDTO produtoRequestDTO);
	
}
