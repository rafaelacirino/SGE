import { Component, OnInit } from '@angular/core';
import { Evento } from 'src/app/dominios/Evento';
import { Usuario } from 'src/app/dominios/Usuario';
import { EventoService } from '../../services/evento.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  eventos: Evento[] = [];
  usuario: Usuario;
  constructor(private servico: EventoService) { }

  ngOnInit(): void {
    this.buscarEventos();
    this.pegarUsuarioLocalStorage();
  }

  private buscarEventos(){
    this.servico.getEventos().subscribe((eventos: Evento[]) => {
      this.eventos = eventos;
    })
  }
  
  pegarUsuarioLocalStorage() {
    const usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    this.usuario = usuario;
  }

}