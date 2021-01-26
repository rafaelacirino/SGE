import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardComponent } from './components/card/card.component';
import { FormularioComponent } from './components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';


const routes: Routes = [
  {
    path:"card",
    component: CardComponent
  },
  {
    path:'listagem',
    component: ListagemComponent
  },
  {
    path:'formulario',
    component: FormularioComponent
  },
  
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
