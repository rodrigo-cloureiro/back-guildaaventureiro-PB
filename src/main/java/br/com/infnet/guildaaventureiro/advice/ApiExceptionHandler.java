package br.com.infnet.guildaaventureiro.advice;

import br.com.infnet.guildaaventureiro.dto.ErrorResponse;
import br.com.infnet.guildaaventureiro.enums.Classe;
import br.com.infnet.guildaaventureiro.exception.EntidadeNaoLocalizadaException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntidadeNaoLocalizadaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadeNaoLocalizadaException(EntidadeNaoLocalizadaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        "Recurso não encontrado",
                        List.of(ex.getMessage())
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentoInvalidoException(MethodArgumentNotValidException ex) {
        List<@Nullable String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        // "Requisição inválida",
                        // "Dados da requisição inválidos",
                        "Falha na validação da requisição",
                        erros
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
        String MESSAGE = "Requisição inválida";
        ResponseEntity.BodyBuilder response = ResponseEntity.status(HttpStatus.BAD_REQUEST);

        if (ex.getMessage().contains("classe"))
            return response.body(new ErrorResponse(
                    MESSAGE,
                    List.of("Erro no JSON da requisição")
            ));

        return response.body(new ErrorResponse(
                MESSAGE,
                List.of(String.format("Classe inválida. Valores permitidos: %s",
                        Arrays.toString(Classe.values())
                ))
        ));
    }
}
