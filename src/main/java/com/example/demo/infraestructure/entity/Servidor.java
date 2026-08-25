package com.example.demo.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "servidor")
@Entity
public class Servidor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "Nome de servidor obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "E-mail é obrigatório")
    @Column(name = "email", unique = true)
    private String email;

    @Past(message = "A data de nascimento deve ser uma data passada")
    @Column(name = "dataDeNascimento")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "secretaria_id")
    @NotNull(message = "Secretaria obrigatória")
    @JsonIgnoreProperties("servidores")
    private Secretaria secretaria;
}