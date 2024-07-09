package br.com.pulse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
}

