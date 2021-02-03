import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiarioErrosComponent } from './components/diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';
import { UsuarioModule } from './modulos/usuario/usuario.module';
import { EventoModule } from './modulos/evento/evento.module';
import { ListagemComponent } from './modulos/evento/components/listagem/listagem.component';


const routes: Routes = [
    { 
    path: '',
    component:ListagemComponent
    },
    { 
    path: 'usuarios',
    loadChildren: () => UsuarioModule, 
    },
    { 
      path: 'eventos',
      loadChildren: () => EventoModule, 
    },
      
    { path: 'diario-erros', component: DiarioErrosComponent, data: { breadcrumb: 'Diário de Erros'} },
    { path: 'login-success', component: LoginSuccessComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
