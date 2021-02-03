import { Component, OnInit } from '@angular/core';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { Usuario } from 'src/app/dominios/Usuario';
import { InscricaoService } from '../../services/inscricao.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  inscricao: Inscricao[] = [];
  usuario: Usuario;
  constructor(private servico: InscricaoService) { }

  ngOnInit(): void {
    this.buscarInscricao();
    this.pegarUsuarioLocalStorage();
  }

  private buscarInscricao(){
    this.servico.getInscricao().subscribe((inscricao: Inscricao[]) => {
      this.inscricao = inscricao;
    })
  }

  pegarUsuarioLocalStorage() {
    const usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    this.usuario = usuario; 
  }

}
