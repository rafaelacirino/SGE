import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Inscricao } from 'src/app/dominios/Inscricao';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { PerguntaService } from 'src/app/modulos/pergunta/servicos/pergunta.service';
import { InscricaoService } from '../../services/inscricao.service';

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
  pergunta = new Perguntas;


  constructor(
    private preguntaService: PerguntaService,
    private fb: FormBuilder,
  ) { 

  }

  ngOnInit(): void {
    
    this.buscarPerguntas()


    this.formInscricao = this.fb.group({
      resposta: ''
    })
  }

  salvar(){
    if (this.formInscricao.invalid){
      alert("Formulario Invalido")
      return
    }
  }

  buscarPerguntas(){
    this.preguntaService.getPerguntas().subscribe((perguntas: Perguntas[]) =>{
      this.perguntas = perguntas
    })
  }

}
