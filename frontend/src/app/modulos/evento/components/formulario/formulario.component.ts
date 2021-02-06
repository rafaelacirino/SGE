import { HttpErrorResponse } from '@angular/common/http';
import { templateJitUrl } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ConfirmationService } from 'primeng';
import { Evento } from 'src/app/dominios/Evento';
import { EventoPergunta } from 'src/app/dominios/EventoPergunta';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { TipoEvento } from 'src/app/dominios/TipoEvento';
import { PerguntaService } from 'src/app/modulos/pergunta/servicos/pergunta.service';
import { EventoService } from '../../services/evento.service';
import {MessageService} from 'primeng/api';


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
  perguntaEscolhidas: Perguntas[] = []
  eventoPergunta: EventoPergunta
  adicionarPergunta: boolean = false
  pergunta = new Perguntas

  tipoEvento: TipoEvento

  constructor(
    private eventoService: EventoService,
    private perguntaService: PerguntaService,
    private fbuilder: FormBuilder,
    private route: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private router: Router,
    private messageService: MessageService  
    ) { 
      
    }

  
  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
  }

  ngOnInit(): void {

    this.route.params.subscribe(params =>{
      if (params.id){
        this.edicao = true;
        this.buscarEvento(params.id)
      }
      
    })

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
      eventoPerguntas: '',
      pergunta: '',
      obrigatorio: ''
    })

  }

  adicionarPerguntas(){
    this.adicionarPergunta = true
  }

  confirm() {
    this.confirmationService.confirm({
        message: 'Deseja salvar mesmo esse evento?',
        accept: () => {
            this.salvar()
        }
    });
  }

  salvar(){
    this.evento.idTipoEvento = this.tipoEvento.id
    this.evento.tipoInsc = this.tipoInsc
   
    if(this.evento.valor == null){
      this.evento.valor = 0
    }

    this.perguntaEscolhidas.forEach(pergunta => {
      this.eventoPergunta = new EventoPergunta
      this.eventoPergunta.idEvento = null
      this.eventoPergunta.idPergunta = pergunta.id
      
      this.evento.perguntas.push(this.eventoPergunta)
    });

    if(this.edicao){
      this.eventoService.editarEvento(this.evento).subscribe(
        evento => {
          this.addSingle("success", "Evento editado", "")
          setTimeout(() => {
            this.router.navigate(['/eventos/listagem'])
          }, 1500)
          this.router.navigate(['/eventos/listagem'])
      }, erro => {
        this.addSingle("warn", "Dados invalidos", erro.error.message)

      });
    } else {
      this.eventoService.salvarEvento(this.evento).subscribe(
        evento => {
          this.addSingle("success", "Evento salvo", "")
          setTimeout(() => {
            this.router.navigate(['/eventos/listagem'])
          }, 1500)
      }, erro => {
        this.addSingle("warn", "Dados invalidos", erro.error.message)

      }
    )
  }
  

  }

  buscarEvento(id: number) {
    this.eventoService.getEvento(id)
      .subscribe((evento: Evento) => {
        this.buscarTipoEvento(evento.idTipoEvento)
        this.evento = evento,
        this.tipoInsc = evento.tipoInsc

        // this.evento.perguntas.forEach(perguntaEvento => {
        //   this.buscarPerguntaPorId(perguntaEvento.idPergunta)
        //   this.perguntaEscolhidas.push(this.pergunta)
        // })
        ;
      }); 
  }

  salvarPergunta(pergunta: Perguntas){
    if(pergunta.obrigatorio == null){
      pergunta.obrigatorio = false
    }

    if (pergunta.obrigatorio){
      pergunta.titulo = this.pergunta.titulo + " *";
    }
    this.perguntaService.salvarPergunta(pergunta).subscribe(
      pergunta => {
        this.addSingle("success", "Pergunta salva", "")
        this.adicionarPergunta = false  
      }, erro => {
        this.addSingle("warn", "Dados invalidos", erro.error.message)

      }
    )
  }

  buscarTipoEventos(){
    this.eventoService.getTipoEventos().subscribe((tipoEventos: TipoEvento[]) =>{
      this.tipoEventos = tipoEventos;
    })
  }

  buscarTipoEvento(id: number){
    this.eventoService.getTipoEvento(id).subscribe(tipoEvento => 
      this.tipoEvento = tipoEvento
      )
  }

  buscarPerguntas(){
    this.perguntaService.getPerguntas().subscribe((perguntas: Perguntas[]) =>{
      this.perguntas = perguntas
    })
  }

  buscarPerguntaPorId(id: number){
    this.perguntaService.getPergunta(id).subscribe((pergunta: Perguntas) =>{
      this.pergunta = pergunta;
    })
  }

}
