import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Secretaria, SecretariaService } from '../../services/secretaria';
import { Servidor, ServidorService } from '../../services/servidor';

@Component({
  selector: 'app-servidor',
  imports: [FormsModule],
  templateUrl: './servidor.html',
  styleUrl: './servidor.css',
})
export class ServidorCadastro implements OnInit {
  private readonly service = inject(ServidorService);
  private readonly secretariaService = inject(SecretariaService);

  readonly nome = signal('');
  readonly email = signal('');
  readonly dataNascimento = signal('');
  readonly secretariaId = signal<number | undefined>(undefined);
  readonly id = signal<number | undefined>(undefined);

  readonly lista = signal<Servidor[]>([]);
  readonly secretarias = signal<Secretaria[]>([]);
  readonly mensagem = signal('');
  readonly erro = signal('');
  readonly salvando = signal(false);

  readonly editando = computed(() => this.id() != null);

  ngOnInit(): void {
    this.carregar();
    this.carregarSecretarias();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (dados) => this.lista.set(dados),
      error: (e) => this.erro.set(this.lerErro(e)),
    });
  }

  carregarSecretarias(): void {
    this.secretariaService.listar().subscribe({
      next: (dados) => this.secretarias.set(dados),
      error: (e) => this.erro.set(this.lerErro(e)),
    });
  }

  salvar(): void {
    this.mensagem.set('');
    this.erro.set('');

    const secretariaId = this.secretariaId();
    const escolhida = this.secretarias().find((item) => item.id === secretariaId);
    const payload: Servidor = {
      id: this.id(),
      nome: this.nome().trim(),
      email: this.email().trim(),
      dataNascimento: this.dataNascimento(),
      secretaria:
        escolhida != null
          ? { id: escolhida.id!, sigla: escolhida.sigla, nomeSecretaria: escolhida.nomeSecretaria }
          : undefined,
    };

    if (!payload.nome || !payload.email || !payload.dataNascimento || payload.secretaria == null) {
      this.erro.set('Preencha nome, e-mail, data de nascimento e secretaria.');
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
            ? 'Servidor atualizado com sucesso.'
            : 'Servidor cadastrado com sucesso.',
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

  editar(item: Servidor): void {
    this.id.set(item.id);
    this.nome.set(item.nome);
    this.email.set(item.email);
    this.dataNascimento.set(item.dataNascimento ?? '');
    this.secretariaId.set(item.secretaria?.id);
    this.mensagem.set('');
    this.erro.set('');
  }

  excluir(item: Servidor): void {
    if (item.id == null) {
      return;
    }
    this.service.deletar(item.id).subscribe({
      next: () => {
        this.mensagem.set('Servidor excluído com sucesso.');
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
    this.nome.set('');
    this.email.set('');
    this.dataNascimento.set('');
    this.secretariaId.set(undefined);
  }

  nomeSecretaria(item: Servidor): string {
    const secretaria = item.secretaria;
    if (!secretaria) {
      return '-';
    }
    if (secretaria.sigla && secretaria.nomeSecretaria) {
      return `${secretaria.sigla} — ${secretaria.nomeSecretaria}`;
    }
    return secretaria.sigla ?? String(secretaria.id);
  }

  private lerErro(erro: HttpErrorResponse): string {
    if (typeof erro.error === 'string' && erro.error.trim()) {
      return erro.error;
    }
    if (erro.status === 0) {
      return 'Não foi possível conectar no backend (http://localhost:8081).';
    }
    return 'Não foi possível salvar o servidor.';
  }
}
