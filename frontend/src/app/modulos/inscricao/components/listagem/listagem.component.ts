import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng';
import { Evento } from 'src/app/dominios/Evento';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { ListagemInscricoes } from 'src/app/dominios/ListagemInscricoes';
import { PreinscricaoUsuario } from 'src/app/dominios/PreinscricaoUsuario';
import { Usuario } from 'src/app/dominios/Usuario';
import { EventoService } from 'src/app/modulos/evento/services/evento.service';
import { InscricaoService } from '../../services/inscricao.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  inscricoes: ListagemInscricoes[] = [];
  inscricoesUsuario: PreinscricaoUsuario[] = [];
  usuario = new Usuario;
  inscricao = new Inscricao;
  @Input() evento = new Evento

  constructor(
    private route: ActivatedRoute,
    private servico: InscricaoService,
    private eventoService: EventoService,
    private messageService: MessageService
    ) { }

    addSingle(error, titulo, corpo ) {
      this.messageService.add({severity:error, summary:titulo, detail:corpo});
    }

  ngOnInit(): void {
    this.pegarUsuarioLocalStorage();

    if(this.usuario.admin){
      this.route.params.subscribe(params =>{
        this.buscarInscricoesPorEvento(params.id)
        this.buscarEvento(params.id)
      })
    }else{
      this.buscarInscricoesPorUsuario()
      
    }
    
  }
  buscarInscricoesPorUsuario(){
    this.servico.getInscricoesPorIdUsuario(this.usuario.id).subscribe((inscricoes: PreinscricaoUsuario[]) => {
      this.inscricoesUsuario = inscricoes
    })
  }

  buscarInscricoesPorEvento(idEvento: number){
    this.servico.getInscricoesPorIdEvento(idEvento).subscribe((inscricoes: ListagemInscricoes[]) => {
      this.inscricoes = inscricoes
     
    })
  }

  editarInscricaoCancelada(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe((inscricao: Inscricao) => {
      inscricao.idSituacaoPreInscricao = 4
      
      this.servico.editarInscricao(inscricao).subscribe(() => {
        this.addSingle("success", "Inscrição cancelada", "")
      });
      location.reload()
    });
  }


  editarInscricaoAprovado(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe((inscricao: Inscricao) => {
      inscricao.idSituacaoPreInscricao = 2
      
      this.servico.editarInscricao(inscricao).subscribe(() => {
        this.addSingle("success", "Inscrição aprovada", "")
      });
      location.reload()
    });
  }

  editarInscricaoReprovada(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe((inscricao: Inscricao) => {
      inscricao.idSituacaoPreInscricao = 3
      
      this.servico.editarInscricao(inscricao).subscribe(() => {
        this.addSingle("success", "Inscrição reprovada", "")
      });
      location.reload()
    });
  }

  buscarInscricaoPorId(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe(inscricao => 
      this.inscricao = inscricao
    );
  }

  pegarUsuarioLocalStorage() {
    this.usuario = JSON.parse(window.localStorage.getItem("usuario")); 
  }

  buscarEvento(id: number) {
    this.eventoService.getEvento(id)
      .subscribe((evento: Evento) => {
        this.evento = evento
      }); 
  }
}
