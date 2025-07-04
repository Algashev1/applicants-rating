import { CommonModule, Location } from '@angular/common';
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
  selectedTab = 0;

  competitionTypes = [
    { key: 'Общий конкурс', label: 'Общий конкурс' },
    { key: 'По договору', label: 'По договору' },
    { key: 'В рамках квоты лиц, имеющих особые права', label: 'Квота особые права' },
    { key: 'Отдельная квота', label: 'Отдельная квота' },
    { key: 'Целевой прием', label: 'Целевой прием' }
  ];

  groupedCurrent: { [key: string]: any[] } = {};
  groupedPrevious: { [key: string]: any[] } = {};
  newPersonalNumbers: { [key: string]: Set<string> } = {};
  disappearedPersonalNumbers: { [key: string]: Set<string> } = {};
  showDisappeared: { [key: string]: boolean } = {};

  availableDates: string[] = [];
  selectedDate: string = '';

  filters: any = {
    admissionType: '',
    spCode: '',
    personalNumber: '',
    totalScore: '',
    individualAchievementTotal: '',
    priority: '',
    finalConsent: '',
    priority1Direction: '',
    priority2Direction: '',
    priority3Direction: '',
    priority4Direction: '',
    priority5Direction: ''
  };

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.directionName = params.get('directionName') || '';
      this.fetchAvailableDates();
    });
  }

  fetchAvailableDates() {
    this.http.get<string[]>(`${this.apiUrl}/api/statements/dates`)
      .subscribe(dates => {
        this.availableDates = dates;
        this.selectedDate = dates[dates.length - 1]; // по умолчанию последняя (самая свежая)
        this.loadStatements();
      });
  }

  loadStatements() {
    this.http.get<any>(
      `${this.apiUrl}/api/statements/direction/${encodeURIComponent(this.directionName)}/with-previous?onlyPriorityOne=${this.onlyPriorityOne}&date=${this.selectedDate}`
    ).subscribe(data => {
      this.currentStatements = data.current.sort((a: any, b: any) => (Number(b.totalScore) || 0) - (Number(a.totalScore) || 0));
      this.previousStatements = data.previous;

      this.groupedCurrent = {};
      this.groupedPrevious = {};
      this.newPersonalNumbers = {};
      this.disappearedPersonalNumbers = {};

      for (const type of this.competitionTypes) {
        this.groupedCurrent[type.key] = this.currentStatements.filter(s => s.admissionType === type.key);
        this.groupedPrevious[type.key] = this.previousStatements.filter(s => s.admissionType === type.key);

        const prevSet = new Set(this.groupedPrevious[type.key].map(s => s.personalNumber));
        const currSet = new Set(this.groupedCurrent[type.key].map(s => s.personalNumber));

        // Новые заявления
        this.newPersonalNumbers[type.key] = new Set(
          this.groupedCurrent[type.key]
            .filter(s => !prevSet.has(s.personalNumber))
            .map(s => s.personalNumber)
        );

        // Пропавшие заявления
        this.disappearedPersonalNumbers[type.key] = new Set(
          this.groupedPrevious[type.key]
            .filter(s => !currSet.has(s.personalNumber))
            .map(s => s.personalNumber)
        );
      }
    });
  }

  get totalNewStatements(): number {
    return Object.values(this.newPersonalNumbers)
      .reduce((sum, set) => sum + (set?.size || 0), 0);
  }

  // Для общего количества пропавших заявлений:
  get totalDisappearedStatements(): number {
    return Object.values(this.disappearedPersonalNumbers)
      .reduce((sum, set) => sum + (set?.size || 0), 0);
  }

  goBack() {
    this.location.back();
  }

  getDisappearedStatements(typeKey: string): any[] {
    const previous = this.groupedPrevious[typeKey] || [];
    const currentSet = new Set((this.groupedCurrent[typeKey] || []).map(s => s.personalNumber));
    return previous.filter(prev => !currentSet.has(prev.personalNumber));
  }

  highlightDirection(direction: string): any {
    if (!direction) return {};
    const prefixes = [
      '01.03.02',
      '02.03.02',
      '03.03.01',
      '09.03.01',
      '10.05.03',
      '11.03.03',
      '11.05.01',
      '12.03.03',
      '12.03.04',
      '12.03.05'
    ];
    const isGreen = prefixes.some(prefix => direction.startsWith(prefix));
    return {
      'background-color': isGreen ? '#00FF00' : '#FF4C4C', // ярко-зелёный или красный
      'color': '#000',
      'font-size': '12px'
    };
  }

  priorityCellStyle(direction: string): any {
    const base = this.highlightDirection(direction) || {};
    if (direction === this.directionName) {
      base['font-weight'] = 'bold';
      base['color'] = 'rgb(0, 0, 255)';
    }
    return base;
  }

  filterRow = (row: any): boolean => {
    return Object.keys(this.filters).every(key => {
      if (!this.filters[key]) return true;
      // Проверяем, есть ли поле в строке
      if (!(key in row)) return true;
      return (row[key] || '').toString().toLowerCase().includes(this.filters[key].toLowerCase());
    });
  };

  getUniqueValues(field: string, typeKey: string): string[] {
    const arr = this.groupedCurrent[typeKey] || [];
    const values = arr.map(s => s[field]).filter(v => v !== undefined && v !== null && v !== '');
    return Array.from(new Set(values)).sort((a, b) => a.localeCompare(b, 'ru'));
  }

  resetFilters() {
    Object.keys(this.filters).forEach(key => this.filters[key] = '');
  }
}
