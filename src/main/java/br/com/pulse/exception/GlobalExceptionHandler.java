// src/main/java/br/com/pulse/exception/GlobalExceptionHandler.java
package br.com.pulse.exception;

import br.com.pulse.exception.produto.ProdutoException;
import br.com.pulse.model.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoException.class)
    public ResponseEntity<ResponseDTO<Void>> handleProdutoException(ProdutoException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
                .body(ResponseDTO.<Void>builder()
                        .data(null)
                        .status(status)
                        .msg(ex.getMessage())
                        .build());
    }
}