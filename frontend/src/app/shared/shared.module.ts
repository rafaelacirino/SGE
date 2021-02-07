import { NgModule } from '@angular/core';
import { PRIMENG_IMPORTS } from './primeng-imports';
import {CardComponent} from './components/card/card.component';
import { LoginComponent } from './components/login/login.component';
import { ConfirmationService } from 'primeng';
import { FormularioComponent } from './components/formulario/formulario.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { InscricaoService } from '../modulos/inscricao/services/inscricao.service';

@NgModule({
    declarations: [CardComponent, LoginComponent, FormularioComponent, DashboardComponent], 
    imports: [
        PRIMENG_IMPORTS,
        ReactiveFormsModule,
        FormsModule
    ],
    providers: [ConfirmationService, InscricaoService],
    exports: [
        PRIMENG_IMPORTS,CardComponent,LoginComponent, FormularioComponent
    ]
})
export class SharedModule { }
