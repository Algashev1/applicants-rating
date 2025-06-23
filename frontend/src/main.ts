import { registerLocaleData } from '@angular/common';
import localeRu from '@angular/common/locales/ru';
import 'zone.js';

registerLocaleData(localeRu);

import { provideHttpClient } from '@angular/common/http';
import { LOCALE_ID } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { App } from './app/app';
import { routes } from './app/app.routes';

bootstrapApplication(App, {
  providers: [
    provideHttpClient(),
    provideRouter(routes),
    { provide: LOCALE_ID, useValue: 'ru-RU' }
  ]
}).catch(err => console.error(err));
