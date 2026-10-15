package com.abdul.biblioteca_api.exception;

public class OperacionInvalidaException extends RuntimeException{
    public OperacionInvalidaException(String mensaje){
        super(mensaje);
    }
}
