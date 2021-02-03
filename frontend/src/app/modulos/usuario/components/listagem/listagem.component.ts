import { EventEmitter, Output, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { Component, Input, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ListagemComponent implements OnInit {
  @Output() emitUsuario: EventEmitter<Usuario> = new EventEmitter;
  @Input() usuario: Usuario
  usuarios: Usuario[] = [];
  admin = false;
  display: boolean = false;

  


  constructor(
    public servico: UsuarioService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit(): void {

    this.buscarUsuarios();

    this.usuario = JSON.parse(localStorage.getItem('usuario'));

    console.log(this.usuario)
  }
  private buscarUsuarios(){
    
    this.servico.getUsuarios()
      .subscribe((usuarios: Usuario[]) => {
        console.log('subscribe')
        this.usuarios = usuarios;
      });
  
    }

    deletarUsuario(id: number) {
      this.servico.deletarUsuario(id)
        .subscribe(() => {
          alert('Usuário deletado');
          this.buscarUsuarios();
        },
        err => alert(err));
    }

    deletarUsuarioLogado(id: number) {
      this.servico.deletarUsuario(id)
        .subscribe(() => {
          alert('Usuário deletado');
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
