import { Component } from '@angular/core';
import { SecretariaCadastro } from './pages/secretaria/secretaria';
import { ServidorCadastro } from './pages/servidor/servidor';

@Component({
  imports: [SecretariaCadastro, ServidorCadastro],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {}
