import { Component, OnInit } from '@angular/core';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { PreinscricaoUsuario } from 'src/app/dominios/PreinscricaoUsuario';
import { Usuario } from 'src/app/dominios/Usuario';
import { InscricaoService } from 'src/app/modulos/inscricao/services/inscricao.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  dataAdmin: any
  dataUsuario: any
  loading: boolean
  inscricoesAprovadas: Inscricao[] = []
  inscricoesReprovadas: Inscricao[] = []
  inscricoesCanceladas: Inscricao[] = []
  inscricoesAguardandoAprovacao: Inscricao[] = []

  inscricoesAprovadasUsuario: PreinscricaoUsuario[] = []
  inscricoesReprovadasUsuario: PreinscricaoUsuario[] = []
  inscricoesCanceladasUsuario: PreinscricaoUsuario[] = []
  inscricoesAguardandoAprovacaoUsuario: PreinscricaoUsuario[] = []
  usuario: Usuario = JSON.parse(localStorage.getItem('usuario'));

  constructor(private inscricaoServico: InscricaoService) { }

  ngOnInit(): void {

    if(!this.usuario.admin){
      this.buscarInscricoesUsuario()
    }else{
      this.buscarInscricoes()
    }


      
  }

  buscarInscricoesUsuario(){
    this.reiniciarListas()
      this.inscricaoServico.getInscricoesPorIdUsuario(this.usuario.id).subscribe((inscricoes: PreinscricaoUsuario[]) => {
        inscricoes.forEach(inscricao => {
          if(inscricao.idSituacao === 1){
            this.inscricoesAguardandoAprovacaoUsuario.push(inscricao);
          }else if(inscricao.idSituacao === 2){
            this.inscricoesAprovadasUsuario.push(inscricao)
          }else if(inscricao.idSituacao === 3){
            this.inscricoesReprovadasUsuario.push(inscricao)
          }else{
            this.inscricoesCanceladasUsuario.push(inscricao)
          }
          
        });
        this.getDataUsuario()
      })
    
  }

  getDataUsuario(){
    this.dataUsuario = {
      labels: ["Aprovada",
              "Reprovada",
              "Cancelada",
              "Aguardando",],
      datasets: [
          {
              data: [
                this.inscricoesAprovadasUsuario.length,
                this.inscricoesReprovadasUsuario.length,
                this.inscricoesCanceladasUsuario.length,
                this.inscricoesAguardandoAprovacaoUsuario.length
              ],
              backgroundColor: [
                "#4BC0C0",
                "#FF6384",
                "#E7E9ED",
                "#FFCE56",
              ],
              hoverBackgroundColor: [
                "#4BC0C0",
                "#FF6384",
                "#E7E9ED",
                "#FFCE56",
              ]
          }]    
      };
  }


  buscarInscricoes(){
      this.reiniciarListas()
      this.inscricaoServico.getInscricao().subscribe((inscricoes: Inscricao[]) =>{
        inscricoes.forEach(inscricao => {
          if(inscricao.idSituacaoPreInscricao === 1){
            this.inscricoesAguardandoAprovacao.push(inscricao);
          }else if(inscricao.idSituacaoPreInscricao === 2){
            this.inscricoesAprovadas.push(inscricao)
          }else if(inscricao.idSituacaoPreInscricao === 3){
            this.inscricoesReprovadas.push(inscricao)
          }else{
            this.inscricoesCanceladas.push(inscricao)
          }
          
        });
        this.getDateAdmin()
      })
    
  }
  
  getDateAdmin(){
    this.dataAdmin = {
      datasets: [{
          data: [
              this.inscricoesAprovadas.length,
              this.inscricoesReprovadas.length,
              this.inscricoesCanceladas.length,
              this.inscricoesAguardandoAprovacao.length
          ],
          backgroundColor: [
              "#4BC0C0",
              "#FF6384",
              "#E7E9ED",
              "#FFCE56",
          ],
          label: 'My dataset'
      }],
      labels: [
          "Aprovada",
          "Reprovada",
          "Cancelada",
          "Aguardando",
      ]
  }
  }

  reiniciarListas(){
    this.inscricoesAguardandoAprovacao = []
    this.inscricoesAprovadas = []
    this.inscricoesCanceladas = []
    this.inscricoesReprovadas = []

    this.inscricoesAprovadasUsuario = []
    this.inscricoesReprovadasUsuario= []
    this.inscricoesCanceladasUsuario= []
    this.inscricoesAguardandoAprovacaoUsuario = []
  }

}
