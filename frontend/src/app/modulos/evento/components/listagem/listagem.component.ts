import { Component, OnInit } from '@angular/core';
import { Evento } from 'src/app/dominios/Evento';
import { EventoService } from '../../services/evento.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  eventos: Evento[] = [];
  constructor(private servico: EventoService) { }

  ngOnInit(): void {
    this.buscarEventos();
  }

  private buscarEventos(){
    this.servico.getEventos().subscribe((eventos: Evento[]) => {
      this.eventos = eventos;
    })
  }

  private buscarTipoEvento(){
    this.servico
  }

}
