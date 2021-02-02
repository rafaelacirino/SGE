import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormularioComponent } from 'src/app/shared/components/formulario/formulario.component';
import { ListagemComponent } from './components/listagem/listagem.component';


const routes: Routes = [

  {
    path:'listagem',
    component: ListagemComponent
  },
  {
    path:'formulario',
    component: FormularioComponent
  },
  {
    path:'formulario/:id',
    component: FormularioComponent
  }
  
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
