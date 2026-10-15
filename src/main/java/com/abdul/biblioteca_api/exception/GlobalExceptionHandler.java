package com.abdul.biblioteca_api.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(IsbnDuplicadoException.class)
    public ResponseEntity<String>isbnDuplicado(IsbnDuplicadoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(LibroNoEncontradoException.class)
    public ResponseEntity<String>libroNoEncontrado(LibroNoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(OperacionInvalidaException.class)
    public ResponseEntity<String>operacionInvalida(OperacionInvalidaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <Map<String,String>>validacion(MethodArgumentNotValidException ex){
        Map<String, String>errores=new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        errores.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
