import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
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

  tipoEvento: TipoEvento

  constructor(
    private eventoService: EventoService,
    private fbuilder: FormBuilder,
    private route: ActivatedRoute
    ) { 
      
    }

  ngOnInit(): void {

    this.buscarTipoEventos()

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
    })

  }

  salvar(){
    this.evento.idTipoEvento = this.tipoEvento.id
    this.evento.tipoInsc = this.tipoInsc
    console.log(this.evento.idTipoEvento)
    this.eventoService.salvarEvento(this.evento).subscribe(
      evento => {alert('Evento salvo')

    }, (erro : HttpErrorResponse) => {
      
      alert(erro.error.message)
    }
    
      
    )
    
  }


  buscarTipoEventos(){
    this.eventoService.getTipoEventos().subscribe((tipoEventos: TipoEvento[]) =>{
      console.log(tipoEventos)
      this.tipoEventos = tipoEventos;
    })
  }

}
