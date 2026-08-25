package com.example.demo.infraestructure.repository;

import com.example.demo.infraestructure.entity.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISecretariaRepository extends JpaRepository<Secretaria, Integer> {

    Optional<Secretaria> findByNomeSecretaria(String sigla);
}
