import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evento } from 'src/app/dominios/Evento';
import { environment } from 'src/environments/environment';

@Injectable()
export class EventoService {

  url = environment.apiUrl

  constructor(private http: HttpClient) { }

  getEventos(): Observable<Evento[]>{

    return this.http.get<Evento[]>(`${this.url}/eventos`)
  }
}
