import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { InscricaoService } from './services/inscricao.service';
import { PerguntaService } from '../pergunta/servicos/pergunta.service';
import { EventoRoutingModule } from '../evento/evento-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InscricaoRoutingModule } from './inscircao-routing.module';


@NgModule({
  declarations: [ FormularioComponent, ListagemComponent],
  providers:[InscricaoService, PerguntaService],
  imports: [
    CommonModule,
    EventoRoutingModule,
    SharedModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    InscricaoRoutingModule
  ]
})
export class InscricaoModule { }
