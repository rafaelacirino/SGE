import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormularioComponent } from '../inscricao/components/formulario/formulario.component';
import { ListagemComponent } from '../inscricao/components/listagem/listagem.component';


const routes: Routes = [
  {
    path: 'listagem',
    component: ListagemComponent
  },
  {
    path: 'listagem/:id',
    component: ListagemComponent
  },
  {
    path: 'formulario',
    component: FormularioComponent
  },
  {
    path: 'formulario/:id',
    component: FormularioComponent
  }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InscricaoRoutingModule { }