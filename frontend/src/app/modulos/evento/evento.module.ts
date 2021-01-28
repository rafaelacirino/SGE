import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventoRoutingModule } from './evento-routing.module';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { EventoService } from './services/evento.service';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [FormularioComponent, ListagemComponent],
  providers:[EventoService],
  imports: [
    CommonModule,
    EventoRoutingModule,
    SharedModule,
    HttpClientModule
  ]
})
export class EventoModule { }
