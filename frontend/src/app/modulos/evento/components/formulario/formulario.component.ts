import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
import { EventoPergunta } from 'src/app/dominios/EventoPergunta';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { TipoEvento } from 'src/app/dominios/TipoEvento';
import { EventoService } from '../../services/evento.service';

@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit {

  edicao = false
  formEvento: FormGroup
  @Input() evento = new Evento
  tipoEventos: TipoEvento[] = []
  tipoInsc: boolean = false
  perguntas: Perguntas[] = []
  perguntaEscolhidas: Perguntas[]
  eventoPergunta: EventoPergunta

  tipoEvento: TipoEvento

  constructor(
    private eventoService: EventoService,
    private fbuilder: FormBuilder,
    private route: ActivatedRoute
    ) { 
      
    }

  ngOnInit(): void {

    this.buscarTipoEventos()
    this.buscarPerguntas()

    this.formEvento = this.fbuilder.group({
      titulo : '',
      periodoInicio: '',
      periodoFim: '',
      tipoInsc: '',
      descricao: '',
      qtdVagas: '',
      idTipoEvento: '',
      valor: '',
      local: '',
      eventoPerguntas: ''
    })

  }

  salvar(){
    this.evento.idTipoEvento = this.tipoEvento.id
    this.evento.tipoInsc = this.tipoInsc

    
    
    this.perguntaEscolhidas.forEach(pergunta => {
      this.eventoPergunta = new EventoPergunta
      this.eventoPergunta.idEvento = null
      this.eventoPergunta.idPergunta = pergunta.id
      

      console.log(this.eventoPergunta)
      this.evento.perguntas.push(this.eventoPergunta)
    });

    console.log(this.evento.perguntas[0])
    this.eventoService.salvarEvento(this.evento).subscribe(
      evento => {alert('Evento salvo')
    }, (erro : HttpErrorResponse) => {
      alert(erro.error.message)
    }
    )
  }

  buscarTipoEventos(){
    this.eventoService.getTipoEventos().subscribe((tipoEventos: TipoEvento[]) =>{
      this.tipoEventos = tipoEventos;
    })
  }

  buscarPerguntas(){
    this.eventoService.getPerguntas().subscribe((perguntas: Perguntas[]) =>{
      this.perguntas = perguntas
    })
  }

}
