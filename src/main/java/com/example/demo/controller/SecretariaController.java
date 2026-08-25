package com.example.demo.controller;

import com.example.demo.infraestructure.entity.Secretaria;
import com.example.demo.usecase.SecretariaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secretaria")
@RequiredArgsConstructor
public class SecretariaController {

    private final SecretariaUseCase sercretariaUseCase;

    @PostMapping
    public ResponseEntity<String> salvarSecretaria(@Valid @RequestBody Secretaria secretaria){
        sercretariaUseCase.SalvarSecretaria(secretaria);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Secretaria>> listarTodasSecretarias(){
        return ResponseEntity.ok(sercretariaUseCase.listarTodasSecretarias());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarSecretariaPorId(@PathVariable Integer id,
                                                       @RequestBody Secretaria secretaria){
        sercretariaUseCase.atualizarSecretariaPorId(id,secretaria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarServidorPorId(@PathVariable Integer id){
        sercretariaUseCase.deletarSecretariaPorId(id);

        return ResponseEntity.ok().body("Secretaria deletada com sucesso!");
    }
}