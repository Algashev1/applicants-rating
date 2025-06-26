import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import './abiturients.component.css'; // Если предпочтительнее использовать styleUrls, можно добавить ниже

export interface Abiturient {
  id: number;
  gender: string;
  birthDate: string;
  personalNumber: string;
  spCode: string;
  benefits: string;
  contactPhone: string;
  homePhone: string;
  mobilePhone: string;
  email: string;
  foreignCitizenship: boolean;
}

@Component({
  selector: 'app-abiturients',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  template: `
      <div class="abiturients-container">
        <h1>Список абитуриентов</h1>
        <div class="search">
          <input type="text" placeholder="Поиск по личному номеру или коду абитуриента" [(ngModel)]="searchTerm" />
          <button (click)="search()">Искать</button>
        </div>
        <table>
          <thead>
            <tr>
              <th>Личный номер</th>
              <th>Код абитуриента</th>
              <th>Пол</th>
              <th>Льготы</th>
              <th>Иностранное гражданство</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let a of abiturients" (click)="openStatements(a)" style="cursor: pointer;">
              <td>{{ a.personalNumber }}</td>
              <td>{{ a.spCode }}</td>
              <td>{{ a.gender }}</td>
              <td>{{ a.benefits }}</td>
              <td>{{ a.foreignCitizenship ? 'Да' : 'Нет' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    `,
  styleUrls: ['./abiturients.component.css']
})
export class AbiturientsComponent implements OnInit {
  abiturients: Abiturient[] = [];
  searchTerm: string = '';
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.loadAbiturients();
  }

  loadAbiturients() {
    this.http.get<Abiturient[]>(`${this.apiUrl}/api/abiturients`)
      .subscribe(data => this.abiturients = data);
  }

  search() {
    if (this.searchTerm) {
      this.abiturients = this.abiturients.filter(a =>
        a.spCode.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        a.personalNumber.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.loadAbiturients();
    }
  }

  openStatements(abiturient: any) {
    this.router.navigate(['/statements', abiturient.personalNumber]);
  }
}