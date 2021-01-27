import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from 'src/app/dominios/Usuario';
import { environment } from 'src/environments/environment';

@Injectable()
export class UsuarioService {

   url = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getUsuarios():Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.url}/usuarios`);
  }

  
}
