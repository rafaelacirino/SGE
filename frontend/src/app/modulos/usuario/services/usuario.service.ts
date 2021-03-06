import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Chave } from 'src/app/dominios/Chave';
import { Usuario } from 'src/app/dominios/Usuario';
import { environment } from 'src/environments/environment';
import {PreinscricaoUsuario} from 'src/app/dominios/PreinscricaoUsuario' 

@Injectable()
export class UsuarioService {


  url = environment.apiUrl;

  constructor(private http: HttpClient) { }

  buscarUsuarioPorChave(chave: Chave){
    return this.http.post<Usuario>(`${this.url}/usuarios/login`, chave);
  }

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
