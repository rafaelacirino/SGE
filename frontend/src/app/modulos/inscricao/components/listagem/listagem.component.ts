import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { ListagemInscricoes } from 'src/app/dominios/ListagemInscricoes';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { Usuario } from 'src/app/dominios/Usuario';
import { EventoService } from 'src/app/modulos/evento/services/evento.service';
import { UsuarioService } from 'src/app/modulos/usuario/services/usuario.service';
import { InscricaoService } from '../../services/inscricao.service';

@Component({
  selector: 'app-listagem',
  templateUrl: './listagem.component.html',
  styleUrls: ['./listagem.component.css']
})
export class ListagemComponent implements OnInit {

  inscricoes: ListagemInscricoes[] = [];
  usuario = new Usuario;
  inscricao = new Inscricao;
  @Input() evento = new Evento

  constructor(
    private route: ActivatedRoute,
    private servico: InscricaoService,
    private eventoService: EventoService
    ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params =>{
      this.buscarInscricoesPorEvento(params.id)
      this.buscarEvento(params.id)
    })


    this.pegarUsuarioLocalStorage();
  }

  buscarInscricoesPorEvento(idEvento: number){
    this.servico.getInscricoesPorIdEvento(idEvento).subscribe((inscricoes: ListagemInscricoes[]) => {
      this.inscricoes = inscricoes
      console.log(this.inscricoes)
    })
  }


  editarInscricaoAprovado(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe((inscricao: Inscricao) => {
      inscricao.idSituacaoPreInscricao = 2
      
      this.servico.editarInscricao(inscricao).subscribe(() => {
        alert("Inscrição aprovada")
      });
      location.reload()
    });
  }

  editarInscricaoReprovada(idInscricao: number){
    this.servico.getInscricaoPorId(idInscricao).subscribe((inscricao: Inscricao) => {
      inscricao.idSituacaoPreInscricao = 3
      
      this.servico.editarInscricao(inscricao).subscribe(() => {
        alert("Inscrição reprovada")
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
