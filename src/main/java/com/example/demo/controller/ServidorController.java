package com.example.demo.controller;

import com.example.demo.infraestructure.entity.Servidor;
import com.example.demo.usecase.ServidorUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/servidor")
@RequiredArgsConstructor
public class ServidorController {

    private final ServidorUseCase servidorUseCase;

    @PostMapping
    public ResponseEntity<String> salvarServidor(@Valid @RequestBody Servidor servidor ){
        servidorUseCase.SalvarServidor(servidor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Servidor>> listarTodosServidor(){
        return ResponseEntity.ok(servidorUseCase.listarTodosServidores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarServidorPorId(@PathVariable Integer id,
                                                       @RequestBody Servidor servidores){
        servidorUseCase.atualizarUsuarioPorId(id, servidores);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServidorPorId(@PathVariable Integer id){
        servidorUseCase.deletarServidorPorId(id);
        return ResponseEntity.ok().build();
    }
}