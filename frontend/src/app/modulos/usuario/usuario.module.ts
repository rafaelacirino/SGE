import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { CardComponent } from './components/card/card.component';
import { CardModule } from 'primeng';


@NgModule({
  declarations: [FormularioComponent, ListagemComponent, CardComponent],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    CardModule
  ]
})
export class UsuarioModule { }
