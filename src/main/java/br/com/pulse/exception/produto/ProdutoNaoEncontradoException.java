package br.com.pulse.exception.produto;

public class ProdutoNaoEncontradoException extends RuntimeException {
	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
