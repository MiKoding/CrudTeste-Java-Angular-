package com.example.demo.infraestructure.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(onConstructor_ = {@JsonCreator})
@Builder
@Table(name = "secretaria")
@Entity
public class Secretaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Sigla da secretaria é obrigatório!")
    @Column(name = "sigla", unique = true)
    private String sigla;

    @Column(name = "nomeSecretaria")
    @NotBlank(message = "Nome da Secretaria é obrigatório!")
    private String nomeSecretaria;

    @OneToMany(mappedBy = "secretaria")
    @JsonIgnoreProperties("secretaria")
    private List<Servidor> servidores = new ArrayList<>();
}
