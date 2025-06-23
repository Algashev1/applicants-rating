import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'; // добавьте этот импорт
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-direction-statements',
  standalone: true,
  imports: [CommonModule, FormsModule], // добавьте FormsModule сюда
  templateUrl: './direction-statements.component.html',
  styleUrls: ['./direction-statements.component.css']
})
export class DirectionStatementsComponent implements OnInit {
  directionName = '';
  statements: any[] = [];
  onlyPriorityOne = false;
  private apiUrl = environment.apiUrl;
  currentStatements: any[] = [];
  previousStatements: any[] = [];
  newPersonalNumberSet = new Set<string>();


  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.directionName = params.get('directionName') || '';
      this.loadStatements();
    });
  }

  loadStatements() {
    this.http.get<any>(`${this.apiUrl}/api/statements/direction/${encodeURIComponent(this.directionName)}/with-previous?onlyPriorityOne=${this.onlyPriorityOne}`)
      .subscribe(data => {
        this.currentStatements = data.current.sort((a: any, b: any) => (Number(b.totalScore) || 0) - (Number(a.totalScore) || 0));
        this.previousStatements = data.previous;
        const prevPersonalNumber = new Set(this.previousStatements.map(s => s.personalNumber));
        this.newPersonalNumberSet = new Set(this.currentStatements.filter(s => !prevPersonalNumber.has(s.personalNumber)).map(s => s.personalNumber));
      });
  }
}
