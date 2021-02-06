import { HttpErrorResponse} from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from 'src/app/modulos/usuario/services/usuario.service';
import {MessageService} from 'primeng/api';
import {PageNotificationService} from "@nuvem/primeng-components";

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
    private pageNotificationService: PageNotificationService,
    private messageService: MessageService,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    
  ) { }

  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
  }


  clear() {
    this.messageService.clear();
  }
  

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
              this.addSingle("success", "Usuário Editado", "Deu Certo")

    if(this.formUsuario.invalid){
      return
    }
  
    if (this.edicao) {
      this.usuarioService.editarUsuario(this.usuario)
        .subscribe(usuario => {
          alert("foi")
          this.pageNotificationService.addSuccessMessage("Foi")
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

