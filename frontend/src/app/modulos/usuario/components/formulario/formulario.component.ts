import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit {

  formUsuario: FormGroup
  usuario = new Usuario();

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    this.formUsuario = this.fb.group({
      nome: ['',Validators.minLength(3)],
      cpf: ['', [Validators.maxLength(11), Validators.minLength(11)]],
      email: ['', Validators.email],
      telefone: '',
      dataNascimento: '' 
    })
  }

  salvar(){
    if(this.formUsuario.invalid){
      alert("Formulario Invalido")
    }

    this.usuarioService.salvarUsuario(this.usuario)
    .subscribe(usuario => {
      console.log('Usuario salvo', this.usuario);
      alert('Usuario salvo')
    }, (erro : HttpErrorResponse) => {
      alert(erro.error.message);
      
    });
  }

}
