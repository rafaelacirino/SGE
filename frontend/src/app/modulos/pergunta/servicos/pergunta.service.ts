import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { environment } from 'src/environments/environment';

@Injectable()
export class PerguntaService {

  url = environment.apiUrl

  constructor(private http: HttpClient) { }

  getPerguntas(): Observable<Perguntas[]>{
    return this.http.get<Perguntas[]>(`${this.url}/perguntas`)
  }

  salvarPergunta(pergunta: Perguntas): Observable<Perguntas>{
    return this.http.post<Perguntas>(`${this.url}/perguntas`, pergunta)
  }


}
