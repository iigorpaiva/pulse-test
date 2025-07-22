package br.com.pulse.exception.produto;

import br.com.pulse.exception.produto.enums.TipoProdutoException;

public class ProdutoException extends RuntimeException {
    private final TipoProdutoException tipo;

    public ProdutoException(TipoProdutoException tipo, String mensagem) {
        super(mensagem);
        this.tipo = tipo;
    }

    public TipoProdutoException getTipo() {
        return tipo;
    }
}