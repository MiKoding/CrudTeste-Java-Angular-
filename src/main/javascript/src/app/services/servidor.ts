import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Servidor {
  id?: number;
  nome: string;
  email: string;
  dataNascimento: string;
  secretaria?: {
    id: number;
    sigla?: string;
    nomeSecretaria?: string;
  };
}

@Injectable({ providedIn: 'root' })
export class ServidorService {
  private readonly http = inject(HttpClient);
  private readonly api = 'http://localhost:8081/servidor';

  listar(): Observable<Servidor[]> {
    return this.http.get<Servidor[]>(this.api);
  }

  salvar(servidor: Servidor): Observable<string> {
    return this.http.post(this.api, servidor, { responseType: 'text' });
  }

  atualizar(id: number, servidor: Servidor): Observable<string> {
    return this.http.put(`${this.api}/${id}`, servidor, { responseType: 'text' });
  }

  deletar(id: number): Observable<string> {
    return this.http.delete(`${this.api}/${id}`, { responseType: 'text' });
  }
}
