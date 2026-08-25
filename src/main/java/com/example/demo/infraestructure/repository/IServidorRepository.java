package com.example.demo.infraestructure.repository;

import com.example.demo.infraestructure.entity.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IServidorRepository extends JpaRepository<Servidor, Integer> {

    Optional<Servidor> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}