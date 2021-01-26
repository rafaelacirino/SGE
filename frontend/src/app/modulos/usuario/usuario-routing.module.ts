import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormularioComponent } from './components/formulario/formulario.component';


const routes: Routes = [
  /*{
    path:'',
    component: ListagemComponent
  },*/
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
