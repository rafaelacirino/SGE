import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  usuarios: Usuario[] = []

  constructor(
    public servico: UsuarioService
    ) {}

  ngOnInit(): void {
    
  this.servico.getUsuarios().subscribe((usuarios: Usuario[])=>{
    console.log('subscribe')
    this.usuarios
  });
  console.log('apos metodo')
  }

}
