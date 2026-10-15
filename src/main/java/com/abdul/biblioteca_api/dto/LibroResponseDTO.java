package com.abdul.biblioteca_api.dto;

public class LibroResponseDTO {
    private String titulo;
    private Long id;
    private String autor;
    private boolean disponible;

    public boolean isDisponible(){
        return disponible;
    }

    public Long getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public LibroResponseDTO(Long id, String titulo, String autor, boolean disponible){
        this.id=id;
        this.titulo=titulo;
        this.autor=autor;
        this.disponible=disponible;
    }


}
