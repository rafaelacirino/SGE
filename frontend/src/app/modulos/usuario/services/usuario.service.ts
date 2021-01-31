import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Usuario } from 'src/app/dominios/Usuario';
import { environment } from 'src/environments/environment';

@Injectable()
export class UsuarioService {


  url = environment.apiUrl;

  constructor(private http: HttpClient) { }

  buscarUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.url}/usuarios/${id}`);
  }


  getUsuarios(): Observable<Usuario[]> {
    
    return this.http.get<Usuario[]>(`${this.url}/usuarios`);
  }

  salvarUsuario(usuario: Usuario): Observable<Usuario>{
    return this.http.post<Usuario>(`${this.url}/usuarios`, usuario);
  }

  editarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.url}/usuarios`, usuario);
  }

  deletarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.url}/usuarios/${id}`);
  }

}
