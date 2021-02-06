import { EventEmitter, Output, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { Component, Input, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng';
import { Usuario } from 'src/app/dominios/Usuario';
import { InscricaoService } from 'src/app/modulos/inscricao/services/inscricao.service';
import { UsuarioService } from '../../services/usuario.service';
import {MessageService} from 'primeng/api';


@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css'],
})
export class ListagemComponent implements OnInit {
  @Output() emitUsuario: EventEmitter<Usuario> = new EventEmitter;
  @Input() usuario: Usuario
  usuarios: Usuario[] = [];
  admin = false;
  display: boolean = false;

  
  

  constructor(
    public servico: UsuarioService,
    private confirmationService: ConfirmationService,
    public servicoInscricao: InscricaoService,
    private messageService: MessageService

  ) { }

  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
  }

  ngOnInit(): void {

    this.buscarUsuarios();
    this.usuario = JSON.parse(localStorage.getItem('usuario'));
    

  }
  private buscarUsuarios(){
    
    this.servico.getUsuarios()
      .subscribe((usuarios: Usuario[]) => {
        this.usuarios = usuarios;
      },
      err => alert(err));
  
    }

    deletarUsuario(id: number) {
      if (id == 1){
        this.addSingle("error", "Admin não pode ser excluido", "")
      }

      if (id != 1){
        this.servico.deletarUsuario(id)
        .subscribe(() => {
          this.addSingle("success", "Usuario excluido", "")
          this.buscarUsuarios();
        },
        err => alert(err));
      }   
    }

    deletarUsuarioLogado(id: number) {
      this.servico.deletarUsuario(id)
        .subscribe(() => {
          this.addSingle("success", "Usuario excluido", "")
          this.buscarUsuarios();
          localStorage.removeItem("usuario")
          location.reload()
        },
        err => alert(err));
    }

    buscarUsuarioPorId(id: number){
      this.servico.buscarUsuarioPorId(id).subscribe((usuario: Usuario) => {
      this.usuario = usuario
      },
      err=> alert(err))
    }
    
    
    
    showDialog() {
      this.display = true;
    }

    confirm(id: number) {
      this.confirmationService.confirm({
          message: 'Deseja mesmo remover sua conta?',
          accept: () => {
              this.deletarUsuarioLogado(id)
              localStorage.removeItem("usuario")
              
          }
      });
    
  }
  receberEdicao(usuarioEditado: Usuario){
    this.display = false
    localStorage.removeItem('usuario');
    localStorage.setItem("usuario", JSON.stringify(usuarioEditado));
    location.reload()
  }
}
