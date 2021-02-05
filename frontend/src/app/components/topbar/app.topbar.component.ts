import { Component } from '@angular/core';
import { AppComponent } from '../../app.component';
import { Authentication, User } from '@nuvem/angular-base';
import { LoginComponent } from 'src/app/shared/components/login/login.component';
import { Usuario } from 'src/app/dominios/Usuario';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopbarComponent {

    user = new Usuario()
    constructor(
        public app: AppComponent,
        private readonly _authentication: Authentication<User>,
        private loginComponent: LoginComponent) {
    }

    buscarNome(){
        
        this.user =  JSON.parse(localStorage.getItem('usuario'))
        return this.user.nome
        
    }
    get usuario() {
        return this._authentication.getUser();
    }

    isAuthenticated() {
        return this._authentication.isAuthenticated();
    }


    logout(){
        this.loginComponent.logout()
        
    }
}
