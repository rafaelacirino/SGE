import { HttpErrorResponse} from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from 'src/app/modulos/usuario/services/usuario.service';

@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit {
  edicao = false
  formUsuario: FormGroup
  @Input() usuario = new Usuario();
  logado = JSON.parse(localStorage.getItem('usuario'));
  @Output() emitDisplay : EventEmitter<boolean> = new EventEmitter
  @Output() emitEdicao : EventEmitter<Usuario> = new EventEmitter


  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute
  ) { }

  

  ngOnInit(): void {
    if(this.logado){
      this.usuario = this.logado
      this.edicao = true
    }
    this.route.params.subscribe(params =>{
      if (params.id){
        this.edicao = true;
        this.buscarUsuario(params.id)
      }
      
    })


    this.formUsuario = this.fb.group({
      nome: ['',Validators.minLength(3)],
      cpf: '', 
      email: ['', Validators.email],
      telefone: '',
      dataNascimento: ''
    })
  }

  buscarUsuario(id: number) {
    this.usuarioService.buscarUsuarioPorId(id)
      .subscribe(usuario => this.usuario = usuario); 
  }



  salvar(){
    if(this.formUsuario.invalid){
      alert("Formulario Invalido")
      return
    }
  
    if (this.edicao) {
      this.usuarioService.editarUsuario(this.usuario)
        .subscribe(usuario => {
          alert('Usuário Editado')
        }, (erro: HttpErrorResponse) => {
          alert(erro.error.message);
        })
        this.emitEdicao.emit(this.usuario);
      } else {
      this.usuarioService.salvarUsuario(this.usuario)
        .subscribe(usuario => {
          alert('Cadastro realizado com sucesso! Chave de acesso enviada. Verifique seu email')
      }, (erro : HttpErrorResponse) => {
        alert(erro.error.message);
      
      });
      
    }
  }

  buscarUsuarioPorId(id: number){
    this.usuarioService.buscarUsuarioPorId(id).subscribe(usuario => {
      this.usuario = usuario
      console.log(this.usuario)
    });
  }

}
