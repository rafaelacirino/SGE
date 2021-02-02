import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evento } from 'src/app/dominios/Evento';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { TipoEvento } from 'src/app/dominios/TipoEvento';
import { environment } from 'src/environments/environment';

@Injectable()
export class EventoService {

  url = environment.apiUrl

  constructor(private http: HttpClient) { }

  getEventos(): Observable<Evento[]>{
    return this.http.get<Evento[]>(`${this.url}/eventos`)
  }

  getTipoEventos(): Observable<TipoEvento[]>{
    return this.http.get<TipoEvento[]>(`${this.url}/tipoevento`)
  }

  salvarEvento(evento: Evento): Observable<Evento>{
    return this.http.post<Evento>(`${this.url}/eventos`, evento)
  }
}
