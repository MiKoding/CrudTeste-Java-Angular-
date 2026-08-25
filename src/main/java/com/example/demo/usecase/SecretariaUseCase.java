package com.example.demo.usecase;

import com.example.demo.exception.RegraNegocioException;
import com.example.demo.infraestructure.entity.Secretaria;
import com.example.demo.infraestructure.repository.ISecretariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretariaUseCase {

    private ISecretariaRepository repository;

    public SecretariaUseCase(ISecretariaRepository repository) {
        this.repository = repository;
    }

    public void SalvarSecretaria(Secretaria secretaria){

        if (repository.findByNomeSecretaria(
                secretaria.getNomeSecretaria()
        ).isPresent()) {

            throw new RegraNegocioException(
                    "Esta secretaria já está cadastrada."
            );
        }

        repository.saveAndFlush(secretaria);
    }

    public List<Secretaria> listarTodasSecretarias(){
        return repository.findAll();
    }

    public void deletarSecretariaPorId(Integer id){
        Secretaria secretaria = repository.findById(id).orElseThrow(() -> new RuntimeException("Secretaria não encontrada!"));

        repository.deleteById(id);
    }

    public void atualizarSecretariaPorId(Integer id, Secretaria secretaria){
        Secretaria secretariaEntity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Usuario não encontrado"));

        Secretaria secretariaAtualizada = Secretaria.builder()
                .sigla(secretaria.getSigla() != null ? secretaria.getSigla() :
                        secretariaEntity.getSigla())
                .nomeSecretaria(secretaria.getNomeSecretaria() != null ? secretaria.getNomeSecretaria() :
                        secretariaEntity.getNomeSecretaria())
                .id(secretariaEntity.getId())
                .build();

        repository.saveAndFlush(secretariaAtualizada);
    }
}
