import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiarioErrosComponent } from './components/diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';
import { UsuarioModule } from './modulos/usuario/usuario.module';
import { EventoModule } from './modulos/evento/evento.module';
import { ListagemComponent } from './modulos/evento/components/listagem/listagem.component';
import { InscricaoModule } from './modulos/inscricao/inscricao.module';
import { DashboardComponent } from './shared/components/dashboard/dashboard.component';

const routes: Routes = [
    { 
    path: '',
    component:DashboardComponent
    },
    { 
    path: 'usuarios',
    loadChildren: () => UsuarioModule, 
    },
    { 
      path: 'eventos',
      loadChildren: () => EventoModule, 
    },
    { 
      path: 'inscricao',
      loadChildren: () => InscricaoModule, 
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
