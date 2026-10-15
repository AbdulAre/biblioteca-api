package com.abdul.biblioteca_api.exception;

public class LibroNoEncontradoException extends RuntimeException{
    public LibroNoEncontradoException(Long id){
        super("No existe un libro con el ID:"+id);
    }
}
