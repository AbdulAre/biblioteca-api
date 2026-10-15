package com.abdul.biblioteca_api.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abdul.biblioteca_api.dto.LibroRequestDTO;
import com.abdul.biblioteca_api.dto.LibroResponseDTO;
import com.abdul.biblioteca_api.exception.IsbnDuplicadoException;
import com.abdul.biblioteca_api.exception.LibroNoEncontradoException;
import com.abdul.biblioteca_api.exception.OperacionInvalidaException;
import com.abdul.biblioteca_api.model.Libro;
import com.abdul.biblioteca_api.repository.LibroRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository rep;

    public LibroResponseDTO crear(LibroRequestDTO dto) {
        if (rep.existsByIsbn(dto.getIsbn())) {
            throw new IsbnDuplicadoException(dto.getIsbn());
        }

        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo(dto.getTitulo());
        nuevoLibro.setAutor(dto.getAutor());
        nuevoLibro.setAnioPublicacion(dto.getAnioPublicacion());
        nuevoLibro.setIsbn(dto.getIsbn());

        Libro libroGuardado = rep.save(nuevoLibro);

        return convertirADTO(libroGuardado);
    }

    private Libro buscar(Long id) {
        return rep.findById(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
    }

    public LibroResponseDTO buscarPorId(Long id) {
        return convertirADTO(buscar(id));
    }

    public List<LibroResponseDTO> listarTodos() {
        return rep.findAll().stream().map(this::convertirADTO).toList();
    }

    public LibroResponseDTO prestar(Long id) {
        Libro libro = buscar(id);
        if (!libro.isDisponible()) {
            throw new OperacionInvalidaException("Ese libro ya esta prestado");
        }
            libro.setDisponible(false);
            Libro guardado = rep.save(libro);
        return convertirADTO(guardado);
    }

    public LibroResponseDTO devolver(Long id) {
        Libro libro = buscar(id);
        if(libro.isDisponible()){
            throw new OperacionInvalidaException("El libro ya ha sido devuelto");
        }
        libro.setDisponible(true);
        Libro guardado = rep.save(libro);
        return convertirADTO(guardado);
    }

    private LibroResponseDTO convertirADTO(Libro libro) {
        return new LibroResponseDTO(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.isDisponible());
    }

    public List<LibroResponseDTO> listarDisponibles(){
        return rep.findAll().stream().filter(libro->libro.isDisponible()).map(this::convertirADTO).toList();
    }

    public Map<String, List <String>>agruparAutor(){
        return rep.findAll().stream().collect(Collectors.groupingBy(libro->libro.getAutor(),Collectors.mapping(libro->libro.getTitulo(),Collectors.toList())));
    }

    public void eliminar(Long id) {
        buscar(id);
        rep.deleteById(id);
    }

    public LibroResponseDTO actualizar(Long id, LibroRequestDTO dto){
        Libro libro = buscar(id);
        if (!dto.getIsbn().equals(libro.getIsbn()) && rep.existsByIsbn(dto.getIsbn())) {
            throw new IsbnDuplicadoException(dto.getIsbn());
        }
        libro.setAutor(dto.getAutor());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setIsbn(dto.getIsbn());
        libro.setTitulo(dto.getTitulo());

        Libro guardado = rep.save(libro);
        return convertirADTO(guardado);
    }


}
