import { Component, OnInit, ViewEncapsulation, } from '@angular/core';
import { Usuario } from 'src/app/dominios/Usuario';
import {UsuarioService} from '../../../modulos/usuario/services/usuario.service'



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  usuario: Usuario;

  chave: string;

  constructor(public servico: UsuarioService) { }

  ngOnInit(): void {
  }

  logarUsuario(chave: string){
    this.servico.buscarUsuarioPorChave(chave).subscribe((usuario :Usuario)=>{
      this.usuario = usuario
    })
    
  }
  onSubmit() {
    return this.chave;
  }

}
