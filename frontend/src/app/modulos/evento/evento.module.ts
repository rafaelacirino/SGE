import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventoRoutingModule } from './evento-routing.module';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { CardComponent } from '../../shared/components/card/card.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [FormularioComponent, ListagemComponent],
  imports: [
    CommonModule,
    EventoRoutingModule,
    SharedModule
  ]
})
export class EventoModule { }
