package dev.imrob.carrental.exceptions.handler;

import dev.imrob.carrental.exceptions.EntityNotFoundException;
import dev.imrob.carrental.exceptions.LoginUniqueViolationException;
import dev.imrob.carrental.exceptions.SenhaIncorretaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private ResponseEntity<CustomError> handlerGenericException(Exception exception, HttpServletRequest request) {

        CustomError error = new CustomError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                LocalDateTime.now().format(formatter),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(SenhaIncorretaException.class)
    private ResponseEntity<CustomError> senhaIncorretaException(SenhaIncorretaException exception, HttpServletRequest request) {

        CustomError error = new CustomError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now().format(formatter),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(
                status.value(),
                "Campo(s) inválido(s)",
                LocalDateTime.now().format(formatter),
                request.getRequestURI()
        );
        for (FieldError f : exception.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        log.error("Erro de validação: " + exception.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(LoginUniqueViolationException.class)
    public ResponseEntity<CustomError> loginUniqueViolationException(LoginUniqueViolationException exception, HttpServletRequest request) {

        CustomError error = new CustomError(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now().format(formatter),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> entityNotFoundException(EntityNotFoundException exception, HttpServletRequest request) {

        CustomError error = new CustomError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now().format(formatter),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
