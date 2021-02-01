import { NgModule } from '@angular/core';
import { PRIMENG_IMPORTS } from './primeng-imports';
import {CardComponent} from './components/card/card.component';
import { LoginComponent } from './components/login/login.component';

@NgModule({
    declarations: [CardComponent, LoginComponent,], 
    imports: [
        PRIMENG_IMPORTS
    ],
    providers: [],
    exports: [
        PRIMENG_IMPORTS,CardComponent,LoginComponent
    ]
})
export class SharedModule { }
