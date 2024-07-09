package br.com.pulse.exception.produto;

public class ProdutoJaExisteException extends RuntimeException {
	public ProdutoJaExisteException(String mensagem) {
		super(mensagem);
	}
}
