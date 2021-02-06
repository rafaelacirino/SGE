import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { InscricaoResposta  } from 'src/app/dominios/InscricaoResposta';
import { EventoService } from 'src/app/modulos/evento/services/evento.service';
import { PerguntaService } from 'src/app/modulos/pergunta/servicos/pergunta.service';
import { Usuario } from 'src/app/dominios/Usuario';
import { InscricaoService } from '../../services/inscricao.service';
import { Router } from "@angular/router"
import {MessageService} from 'primeng/api';



@Component({
  selector: 'app-formulario',
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.css']
})
export class FormularioComponent implements OnInit {

  formInscricao: FormGroup;
  @Input() inscricao = new Inscricao;
  perguntas: Perguntas[] = []
  resposta: string;
  evento = new Evento()
  usuario = new Usuario()
  inscricaoResposta = new InscricaoResposta()
  inscricaoRespostas: InscricaoResposta[] = [] 



  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private eventoServico: EventoService,
    private perguntaServico: PerguntaService,
    private inscricaoServico: InscricaoService,
    private messageService: MessageService

  ) { 

  }

  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
  }

  ngOnInit(): void {
    
    this.route.params.subscribe(params =>{
        this.buscarEvento(params.id)
    })
    
  }

  

  salvarResposta(){
    this.usuario = JSON.parse(window.localStorage.getItem("usuario")); 
    let cond = true;

    this.perguntas.forEach(pergunta => {
      console.log(pergunta.resposta)
      if (!pergunta.resposta && pergunta.obrigatorio){
        cond = false;
        this.addSingle("warn", "Você não respondeu uma pergunta obrigatoria", "")
        
        setTimeout(() => {
          location.reload();
        }, 1500);
      
      }
      
      this.inscricaoResposta = new InscricaoResposta
      this.inscricaoResposta.idEvento = this.evento.id
      this.inscricaoResposta.idPergunta = pergunta.id
      this.inscricaoResposta.idInscricao = null
      this.inscricaoResposta.resposta = pergunta.resposta

      this.inscricaoRespostas.push(this.inscricaoResposta)
    });

    if (cond){

      this.inscricao.idEvento = this.evento.id
      this.inscricao.idUsuario = this.usuario.id
      this.inscricao.idSituacaoPreInscricao = 1
      this.inscricaoRespostas.forEach(resposta => {
        this.inscricao.inscricaoRespostas.push(resposta)
      });

      this.inscricaoServico.salvarInscricao(this.inscricao).subscribe(
        inscricao => {
          this.addSingle("success", "Inscrição salva", "")
   
        }, erro => {
          this.addSingle("warn", "Dados invalidos", erro.error.message)
        })
      setTimeout(() => {
        this.router.navigate(['/eventos/listagem'])
      }, 1500);
      
    }    
  }

  buscarEvento(id: number){
    this.perguntas = []

    this.eventoServico.getEvento(id).subscribe((evento: Evento) => {
        this.evento = evento
        this.evento.perguntas.forEach(eventoPergunta => {
          this.buscarPerguntaDoEvento(eventoPergunta.idPergunta)
        });

      } )
  }

  buscarPerguntaDoEvento(id: number){
    this.perguntaServico.getPergunta(id).subscribe((pergunta: Perguntas) =>{

      this.perguntas.push(pergunta)
    })
  }
}
