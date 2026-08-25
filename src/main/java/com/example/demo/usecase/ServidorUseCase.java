package com.example.demo.usecase;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.infraestructure.entity.Servidor;
import com.example.demo.infraestructure.repository.IServidorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
public class ServidorUseCase {

    private IServidorRepository repository;

    public ServidorUseCase(IServidorRepository repository) {
        this.repository = repository;
    }

    public void SalvarServidor(Servidor servidor){
        ValidaRegistro(servidor);
        repository.saveAndFlush(servidor);
    }

    public List<Servidor> listarTodosServidores(){
        return repository.findAll();
    }

    public void deletarServidorPorId(Integer id){

        if(repository.findById(id).isEmpty()){
            throw new RegraNegocioException("Servidor não encontrado");
        }

        repository.deleteById(id);
    }

    public void atualizarUsuarioPorId(Integer id, Servidor servidores){
        Servidor servidorEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Servidor não encontrado"));

        Servidor servidoresAtualizado = Servidor.builder()
                .email(servidores.getEmail() != null ? servidores.getEmail() :
                        servidorEntity.getEmail())
                .nome(servidores.getNome() != null ? servidores.getNome() :
                        servidorEntity.getNome())
                .dataNascimento(servidores.getDataNascimento() != null ? servidores.getDataNascimento() :
                        servidorEntity.getDataNascimento())
                .secretaria(servidores.getSecretaria() != null ? servidores.getSecretaria() :
                        servidorEntity.getSecretaria())
                .id(servidorEntity.getId())
                .build();

        repository.saveAndFlush(servidoresAtualizado);
    }

    private void ValidaRegistro(Servidor servidor){
        LocalDate hoje = LocalDate.now();

        int idade = Period.between(
                servidor.getDataNascimento(),
                hoje
        ).getYears();

        if (idade < 18 || idade > 75) {
            throw new RegraNegocioException(
                    "O usuário deve ter entre 18 e 75 anos."
            );
        }

        if (repository.findByEmail(servidor.getEmail()).isPresent()) {

            throw new RegraNegocioException(
                    "Já existe um servidor cadastrado com este e-mail."
            );
        }

    }

}
