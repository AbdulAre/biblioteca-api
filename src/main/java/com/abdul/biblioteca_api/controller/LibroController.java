package com.abdul.biblioteca_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.biblioteca_api.dto.LibroRequestDTO;
import com.abdul.biblioteca_api.dto.LibroResponseDTO;
import com.abdul.biblioteca_api.service.LibroService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService service; 

    @PostMapping 
    public LibroResponseDTO crear(@Valid @RequestBody LibroRequestDTO dto){
        return service.crear(dto);
    }

    @GetMapping("/{id}")   
    public LibroResponseDTO buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PatchMapping("/{id}/prestar")
    public LibroResponseDTO prestar(@PathVariable Long id){
        return service.prestar(id);
    }

    @PatchMapping("/{id}/devolver")
    public LibroResponseDTO devolver(@PathVariable Long id){
        return service.devolver(id);
    }

    @GetMapping
    public List<LibroResponseDTO>listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/por-autor")
    public Map<String,List<String>>agruparAutor(){
        return service.agruparAutor();
    }

    @GetMapping("/disponibles")
    public List<LibroResponseDTO>listarDisponibles(){
        return service.listarDisponibles();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public LibroResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequestDTO dto){
        return service.actualizar(id, dto);
    }
}

