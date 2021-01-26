import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';


if (environment.production) {
    enableProdMode();
}

platformBrowserDynamic()
<<<<<<< HEAD
    .bootstrapModule(AppModule)
    .catch(err => console.log(err));
=======
 .bootstrapModule(AppModule)
 .catch(err => console.log(err));
>>>>>>> 2a9c348bf48714a7a6f4a2d858b20f1dc40aa0ce

