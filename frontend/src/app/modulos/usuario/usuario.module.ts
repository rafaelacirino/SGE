import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';
import { CardComponent } from '../../shared/components/card/card.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { UsuarioService } from './services/usuario.service';
import { HttpClientModule } from '@angular/common/http';


@NgModule({

  exports:[CardComponent],
  declarations: [FormularioComponent, ListagemComponent],
  providers: [UsuarioService],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    SharedModule,
    HttpClientModule
  ]
})
export class UsuarioModule { }
