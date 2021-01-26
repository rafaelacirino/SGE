import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { CardComponent } from '../../shared/components/card/card.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({

  exports:[CardComponent],
  declarations: [FormularioComponent, ListagemComponent],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    SharedModule
  ]
})
export class UsuarioModule { }
