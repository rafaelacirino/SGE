import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { Usuario } from 'src/app/dominios/Usuario';
import { UsuarioService } from 'src/app/modulos/usuario/services/usuario.service';
import { InscricaoService } from '../../services/inscricao.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  inscricoes: Inscricao[] = [];
  usuario: Usuario;
  evento: Evento

  constructor(
    private route: ActivatedRoute,
    private servico: InscricaoService,
    private usuarioServico: UsuarioService
    ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.buscarInscricoesPorEvento(params.id)
  })
  }

  buscarInscricoesPorEvento(idEvento: number){
    this.servico.getInscricoesPorIdEvento(idEvento).subscribe((inscricoes: Inscricao[]) => {
      this.inscricoes = inscricoes
    })
  }

  buscarUsuarioDaInscricao(idUsuario: number){
    this.usuarioServico.buscarUsuarioPorId(idUsuario).subscribe((usuario: Usuario) => {
      return usuario;
    })
  }

  private buscarInscricao(){
    this.servico.getInscricao().subscribe((inscricoes: Inscricao[]) => {
      this.inscricoes = inscricoes;
    })
  }

  pegarUsuarioLocalStorage() {
    const usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    this.usuario = usuario; 
  }

}
