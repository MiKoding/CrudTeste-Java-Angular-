import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Secretaria {
  id?: number;
  sigla: string;
  nomeSecretaria: string;
}

@Injectable({ providedIn: 'root' })
export class SecretariaService {
  private readonly http = inject(HttpClient);
  private readonly api = 'http://localhost:8081/secretaria';

  listar(): Observable<Secretaria[]> {
    return this.http.get<Secretaria[]>(this.api);
  }

  salvar(secretaria: Secretaria): Observable<string> {
    return this.http.post(this.api, secretaria, { responseType: 'text' });
  }

  atualizar(id: number, secretaria: Secretaria): Observable<string> {
    return this.http.put(`${this.api}/${id}`, secretaria, { responseType: 'text' });
  }

  deletar(id: number): Observable<string> {
    return this.http.delete(`${this.api}/${id}`, { responseType: 'text' });
  }
}
