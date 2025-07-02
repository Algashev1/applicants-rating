import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterLink, RouterOutlet, HttpClientModule, RouterModule],
  template: `
    <nav class="main-navbar">
  <div class="navbar-container">
    <a routerLink="/" class="navbar-logo">Рейтинг абитуриентов</a>
    <div class="navbar-links">
      <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact:true}">Институты</a>
      <a routerLink="/abiturients" routerLinkActive="active">Абитуриенты</a>
      <a routerLink="/upload" routerLinkActive="active">Файлы</a>
    </div>
  </div>
</nav>
<router-outlet></router-outlet>
  `
})
export class App { }
