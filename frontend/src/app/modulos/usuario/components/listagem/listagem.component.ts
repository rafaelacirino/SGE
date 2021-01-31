import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  usuario: Usuario
  usuarios: Usuario[] = [];

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

    
  

}
