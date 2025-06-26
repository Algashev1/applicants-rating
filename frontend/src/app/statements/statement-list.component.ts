import { CommonModule, Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../environments/environment';

export interface Statement {
  id: number;
  admissionType: string;
  priority: string;
  overallPriority: string;
  institute: string;
  trainingDirection: string;
  targetedAdmission: string;
  separateQuota: string;
  specialQuota: string;
  totalScore: string;
  costReimbursementType: string;
  individualAchievementAdvantageTotal: string;
  importDate: string;
  submissionDate: string;
  spCode: string;
  // ... другие поля ...
  [key: string]: string | number | undefined;
}

@Component({
  selector: 'app-statement-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './statement-list.component.html',
  styleUrls: ['./statement-list.component.css']
})
export class StatementListComponent implements OnInit {
  spCode: string = "";
  personalNumber: string = "";
  statements: Statement[] = [];
  filteredStatements: Statement[] = [];
  budgetStatements: Statement[] = [];
  contractStatements: Statement[] = [];
  availableDates: string[] = [];
  selectedDate: string = '';
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router, private location: Location) { }

  ngOnInit(): void {
    this.personalNumber = this.route.snapshot.paramMap.get('personalNumber') || "";

    this.http.get<Statement[]>(`${this.apiUrl}/api/statements/by-personalNumber/${this.personalNumber}`)
      .subscribe(data => {
        this.statements = data || [];
        // Получить все уникальные даты, отсортировать по убыванию
        this.availableDates = Array.from(new Set(this.statements.map(s => s.importDate)))
          .filter(d => !!d)
          .sort((a, b) => b.localeCompare(a));
        // По умолчанию выбрать самую новую дату
        this.selectedDate = this.availableDates[0] || '';
        this.filterByDate();
        this.statements.forEach(statement => {
          this.spCode = statement.spCode;
        });
      });
  }

  onDateChange(event: any) {
    this.filterByDate();
  }

  filterByDate() {
    this.filteredStatements = this.statements.filter(s => s.importDate === this.selectedDate);
    // Сортировка по возрастанию приоритета
    this.filteredStatements = this.filteredStatements.sort((a, b) => {
      const pa = Number(a.priority);
      const pb = Number(b.priority);
      if (isNaN(pa) && isNaN(pb)) return 0;
      if (isNaN(pa)) return 1;
      if (isNaN(pb)) return -1;
      return pa - pb;
    });
    // Разделение по виду размещения затрат
    this.budgetStatements = this.filteredStatements.filter(s => (s.costReimbursementType || '').toLowerCase().includes('бюджет'));
    this.contractStatements = this.filteredStatements.filter(s => (s.costReimbursementType || '').toLowerCase().includes('договор'));
  }

  goBack(): void {
    this.location.back();
  }

  getEgeResults(statement: Statement) {
    const egeFields = [
      { key: 'math', label: 'Математика' },
      { key: 'phys', label: 'Физика' },
      { key: 'chem', label: 'Химия' },
      { key: 'bio', label: 'Биология' },
      { key: 'informatics', label: 'Информатика' },
      { key: 'history', label: 'История' },
      { key: 'literature', label: 'Литература' },
      { key: 'anya', label: 'Английский язык' },
      { key: 'nemya', label: 'Немецкий язык' },
      { key: 'ry', label: 'Русский язык' },
      { key: 'frya', label: 'Французский язык' },
      { key: 'common', label: 'Обществознание' },
      { key: 'mpProfile', label: 'Математика профильная' },
      { key: 'elmpProfile', label: 'Электроника профильная' },
      { key: 'osInRpProfile', label: 'Основы информатики профильная' },
      { key: 'informaticsProfile', label: 'Информатика профильная' },
      { key: 'bioProfile', label: 'Биология профильная' },
      { key: 'litProfile', label: 'Литература профильная' },
      { key: 'historyProfile', label: 'История профильная' },
      { key: 'commonProfile', label: 'Обществознание профильное' },
      { key: 'matVo', label: 'Математика (вступит. испытание)' },
      { key: 'physVo', label: 'Физика (вступит. испытание)' },
      { key: 'commonVo', label: 'Обществознание (вступит. испытание)' },
      { key: 'historyVo', label: 'История (вступит. испытание)' },
      { key: 'informaticsVo', label: 'Информатика (вступит. испытание)' },
      { key: 'englishVo', label: 'Английский (вступит. испытание)' },
      { key: 'germanVo', label: 'Немецкий (вступит. испытание)' },
      { key: 'litVo', label: 'Литература (вступит. испытание)' },
      { key: 'bioVo', label: 'Биология (вступит. испытание)' },
      { key: 'chemVo', label: 'Химия (вступит. испытание)' },
      { key: 'creativeExam', label: 'Творческий экзамен' },
      { key: 'ry2', label: 'Русский язык (альт.)' },
      { key: 'm', label: 'Математика (альт.)' },
      { key: 'f', label: 'Физика (альт.)' },
      { key: 'h', label: 'Химия (альт.)' },
      { key: 'b', label: 'Биология (альт.)' },
      { key: 'i', label: 'Информатика (альт.)' },
      { key: 'g', label: 'География' },
      { key: 'aya', label: 'Английский язык (альт.)' },
      { key: 'nya', label: 'Немецкий язык (альт.)' },
      { key: 'fya', label: 'Французский язык (альт.)' },
      { key: 'o', label: 'Обществознание (альт.)' },
      { key: 'l', label: 'Литература (альт.)' },
      { key: 'iya', label: 'Итальянский язык' },
      { key: 'ikt', label: 'ИКТ' },
      { key: 'kya', label: 'Китайский язык' },
      { key: 'ity', label: 'Испанский язык' }
    ];
    return egeFields
      .map(f => ({ label: f.label, value: statement[f.key] }))
      .filter(f => f.value && !isNaN(Number(f.value)) && Number(f.value) > 0);
  }
}