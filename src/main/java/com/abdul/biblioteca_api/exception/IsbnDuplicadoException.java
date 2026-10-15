package com.abdul.biblioteca_api.exception;

public class IsbnDuplicadoException extends RuntimeException{

    public IsbnDuplicadoException(String isbn){
        super("Este isbn esta duplicado:"+ isbn);
    }
}
