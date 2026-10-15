package com.abdul.biblioteca_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class LibroRequestDTO{

    @NotBlank(message = "Debe especificar el titulo")
    private String titulo;

    @NotBlank(message = "Debe especificar el autor")
    private String autor;

    @Min(value=1450, message="El anio debe ser mayor a 1450")
    private int anioPublicacion;

    @NotBlank(message = "Debe especificar un isbn")
    private String isbn;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    
}