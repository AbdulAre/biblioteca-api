package com.abdul.biblioteca_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdul.biblioteca_api.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    boolean existsByIsbn(String isbn);
}
