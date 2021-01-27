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

  constructor(
    public servico: EventoService
  ) { }

  ngOnInit(): void {

    this.buscarEventos();
  }

  private buscarEventos(){
    
    this.servico.getEventos()
      .subscribe((eventos: Evento[]) => {
        console.log('subscribe')
        this.eventos = eventos;
      });
  
    }
  

}