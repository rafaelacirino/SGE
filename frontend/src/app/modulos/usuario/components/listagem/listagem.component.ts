import { ViewEncapsulation } from '@angular/core';
import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ListagemComponent implements OnInit {

  @Input() usuario: Usuario
  usuarios: Usuario[] = [];
  admin = false;

  constructor(
    public servico: UsuarioService
  ) { }

  ngOnInit(): void {

    this.buscarUsuarios();
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

    buscarUsuarioPorId(id: number){
      this.servico.buscarUsuarioPorId(id).subscribe((usuario: Usuario) => {
      this.usuario = usuario
      },
      err=> alert(err))
    }

    
  

}
