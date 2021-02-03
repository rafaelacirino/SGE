import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chave } from 'src/app/dominios/Chave';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { environment } from 'src/environments/environment';

@Injectable()

export class InscricaoService {

  url = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getInscricao(): Observable<Inscricao[]>{
    return this.http.get<Inscricao[]>(`${this.url}/preinscricao`)
  }


  salvarInscricao(inscricao: Inscricao): Observable<Inscricao>{
    return this.http.post<Inscricao>(`${this.url}/preinscricao`, inscricao)
  }

  deletarInscricaoChave(chave: Chave): Observable<any> {
    return this.http.delete(`${this.url}/preinscricao/${chave}`);
  }
}
