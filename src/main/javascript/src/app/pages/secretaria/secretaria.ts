import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Secretaria, SecretariaService } from '../../services/secretaria';

@Component({
  selector: 'app-secretaria',
  imports: [FormsModule],
  templateUrl: './secretaria.html',
  styleUrl: './secretaria.css',
})
export class SecretariaCadastro implements OnInit {
  private readonly service = inject(SecretariaService);

  readonly sigla = signal('');
  readonly nomeSecretaria = signal('');
  readonly id = signal<number | undefined>(undefined);
  readonly lista = signal<Secretaria[]>([]);
  readonly mensagem = signal('');
  readonly erro = signal('');
  readonly salvando = signal(false);

  readonly editando = computed(() => this.id() != null);

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (dados) => this.lista.set(dados),
      error: (e) => this.erro.set(this.lerErro(e)),
    });
  }

  salvar(): void {
    this.mensagem.set('');
    this.erro.set('');

    const payload: Secretaria = {
      id: this.id(),
      sigla: this.sigla().trim(),
      nomeSecretaria: this.nomeSecretaria().trim(),
    };

    if (!payload.sigla || !payload.nomeSecretaria) {
      this.erro.set('Preencha sigla e nome da secretaria.');
      return;
    }

    this.salvando.set(true);
    const pedido = this.editando()
      ? this.service.atualizar(payload.id!, payload)
      : this.service.salvar(payload);

    pedido.subscribe({
      next: () => {
        this.mensagem.set(
          this.editando()
            ? 'Secretaria atualizada com sucesso.'
            : 'Secretaria cadastrada com sucesso.',
        );
        this.limpar();
        this.carregar();
        this.salvando.set(false);
      },
      error: (e) => {
        this.erro.set(this.lerErro(e));
        this.salvando.set(false);
      },
    });
  }

  editar(item: Secretaria): void {
    this.id.set(item.id);
    this.sigla.set(item.sigla);
    this.nomeSecretaria.set(item.nomeSecretaria);
    this.mensagem.set('');
    this.erro.set('');
  }

  excluir(item: Secretaria): void {
    if (item.id == null) {
      return;
    }
    this.service.deletar(item.id).subscribe({
      next: () => {
        this.mensagem.set('Secretaria excluída com sucesso.');
        this.erro.set('');
        if (this.id() === item.id) {
          this.limpar();
        }
        this.carregar();
      },
      error: (e) => this.erro.set(this.lerErro(e)),
    });
  }

  limpar(): void {
    this.id.set(undefined);
    this.sigla.set('');
    this.nomeSecretaria.set('');
  }

  private lerErro(erro: HttpErrorResponse): string {
    if (typeof erro.error === 'string' && erro.error.trim()) {
      return erro.error;
    }
    if (erro.status === 0) {
      return 'Não foi possível conectar no backend (http://localhost:8081).';
    }
    return 'Não foi possível salvar a secretaria.';
  }
}
