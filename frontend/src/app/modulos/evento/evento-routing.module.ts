import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Evento } from 'src/app/dominios/Evento';
import { CardComponent } from 'src/app/shared/components/card/card.component';
import { FormularioComponent } from '../evento/components/formulario/formulario.component';
import { ListagemComponent } from '../evento/components/listagem/listagem.component';


const routes: Routes = [
  {
    path: 'card',
    component: CardComponent
  },
  {
    path: 'listagem',
    component: ListagemComponent
  },
  {
    path: 'formulario',
    component: FormularioComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventoRoutingModule { }
