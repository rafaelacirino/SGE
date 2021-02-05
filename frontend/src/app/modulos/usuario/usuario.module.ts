import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { ListagemComponent } from './components/listagem/listagem.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { UsuarioService } from './services/usuario.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InscricaoService } from '../inscricao/services/inscricao.service';


@NgModule({

  declarations: [ ListagemComponent,],
  providers: [UsuarioService, InscricaoService],
  imports: [
    CommonModule,
    UsuarioRoutingModule,
    SharedModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule

  ]
})
export class UsuarioModule { }
