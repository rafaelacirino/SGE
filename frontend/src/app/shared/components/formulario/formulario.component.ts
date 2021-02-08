import { HttpErrorResponse} from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from 'src/app/modulos/usuario/services/usuario.service';
import {ConfirmationService, MessageService} from 'primeng/api';

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

  @Output() emitEdicao = new  EventEmitter<Usuario>() 
  @Output() emitCadastro = new EventEmitter<Usuario>()


  constructor(
    private messageService: MessageService,
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute,
    
  ) { }

  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
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

  confirm() {
    this.confirmationService.confirm({
        message: 'Deseja mesmo se cadastrar?',
        accept: () => {
            this.salvar()
        }
    });
  }

  salvar(){
    if(this.formUsuario.invalid){
      return
    }
  
    if (this.edicao) {
      this.usuarioService.editarUsuario(this.usuario)
        .subscribe(usuario => {
          this.addSingle("success", "Usuário Editado", "")
        }, erro => {
          this.addSingle('warn', "Dados Invalidos", erro.error.message)
        })
        this.emitEdicao.emit(this.usuario);
      } else {
        this.usuarioService.salvarUsuario(this.usuario)
          .subscribe(usuario => {
            this.addSingle("success", "Cadastro com Sucesso!", "Verifique seu email")
       }, erro => {
          this.addSingle('warn', "Dados Invalidos", erro.error.message)
        }
      );
       this.emitCadastro.emit(this.usuario);
      
    }
  }

  buscarUsuarioPorId(id: number){
    this.usuarioService.buscarUsuarioPorId(id).subscribe(usuario => {
      this.usuario = usuario
    });
  }

}

