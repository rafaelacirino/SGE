import { Component, OnInit } from '@angular/core';
import { Evento } from 'src/app/dominios/Evento';
import { EventoPergunta } from 'src/app/dominios/EventoPergunta';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { TipoEvento } from 'src/app/dominios/TipoEvento';
import { Usuario } from 'src/app/dominios/Usuario';
import { PerguntaService } from 'src/app/modulos/pergunta/servicos/pergunta.service';
import { EventoService } from '../../services/evento.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  eventos: Evento[] = [];
  evento = new Evento()
  usuario: Usuario;
  display: boolean = false;
  tipoEvento: string
  perguntas: Perguntas[]
  constructor(private servico: EventoService, private perguntaService: PerguntaService) { }

  ngOnInit(): void {
    this.buscarEventos();
    this.pegarUsuarioLocalStorage();
    
  }

  buscarTipoEvento(id: number){
    this.servico.getTipoEvento(id).subscribe((tipoEvento: TipoEvento ) => { 
      this.tipoEvento = tipoEvento.descricao;
    })  
    
  }
  buscarEventos(){
    this.servico.getEventos().subscribe((eventos: Evento[]) => {
      this.eventos = eventos;
    })
  }
  
  pegarUsuarioLocalStorage() {
    const usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    this.usuario = usuario;
  }

  showDialog(id: number) {
    this.servico.getEvento(id)
      .subscribe(evento => {
        this.evento = evento
        this.buscarTipoEvento(evento.idTipoEvento)
        this.buscarPerguntasDoEvento(evento.perguntas)
        this.display = true;
      }); 
  }
  
  buscarPerguntasDoEvento(eventoPerguntas: EventoPergunta[]){
    this.perguntas = []
    eventoPerguntas.forEach(pergunta => {
      this.perguntaService.getPergunta(pergunta.idPergunta).subscribe((pergunta2 => {
        this.perguntas.push(pergunta2)
      }))
    });
  }

  fecharDialog(usuarioSalvo: Usuario) {
    this.display = false;
    this.buscarEventos();
  }


}