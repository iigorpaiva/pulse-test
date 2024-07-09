package br.com.pulse.exception.produto;

public class ProdutoInvalidoException extends RuntimeException {

	public ProdutoInvalidoException(String mensagem){
		super(mensagem);
	}
	
}
