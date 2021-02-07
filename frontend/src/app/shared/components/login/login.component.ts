import { Component, EventEmitter, OnInit, Output, ViewEncapsulation, } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Chave } from 'src/app/dominios/Chave';
import { Usuario } from 'src/app/dominios/Usuario';
import {UsuarioService} from '../../../modulos/usuario/services/usuario.service'
import { Router } from "@angular/router"


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  @Output() emitUsuario = new EventEmitter<Usuario>()
  display: boolean = false;
  chaveInput: string
  formChave: FormGroup
  chave = new Chave();

  constructor(
    private router: Router,
    public servico: UsuarioService,
    private fbuilder: FormBuilder) { }

  ngOnInit(): void {
    this.pegarUsuarioLocalStorage();

    this.formChave = this.fbuilder.group({
      chave: ''
    })
  }

  pegarUsuarioLocalStorage() {
    const usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    this.emitUsuario.emit(usuario);
  }

  logarUsuario(chaveInput: string){
    this.router.navigate(['/'])
    this.chave.chave = chaveInput
    this.servico.buscarUsuarioPorChave(this.chave).subscribe((usuario :Usuario)=>{
      this.emitUsuario.emit(usuario);
      localStorage.setItem("usuario", JSON.stringify(usuario));
    })
    
  }

  showDialog() {
      this.display = true;
  }

  fecharDialog() {
    this.display = false;
  }

  logout(){
    localStorage.clear()
    location.reload()
  }

}
