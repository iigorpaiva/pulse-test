package br.com.pulse.mapper;

import br.com.pulse.model.dto.ProdutoRequestDTO;
import br.com.pulse.model.dto.ProdutoResponseDTO;
import br.com.pulse.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {

	ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

	/**
	 * Converte ProdutoRequestDTO para Produto (ignora o id).
	 * @param produtoRequestDTO objeto origem
	 * @return Produto convertido
	 */
	@Mapping(target = "id", ignore = true)
	Produto produtoRequestDtoToProduto(ProdutoRequestDTO produtoRequestDTO);

	/**
	 * Converte Produto para ProdutoResponseDTO.
	 * @param produto objeto origem
	 * @return ProdutoResponseDTO convertido
	 */
	ProdutoResponseDTO produtoToProdutoResponseDto(Produto produto);
}