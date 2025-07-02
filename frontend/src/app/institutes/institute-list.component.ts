import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-institute-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './institute-list.component.html',
  styleUrls: ['./institute-list.component.css']
})
export class InstituteListComponent implements OnInit {
  institutes: any[] = [];
  private apiUrl = environment.apiUrl;
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.http.get<any[]>(`${this.apiUrl}/api/institutes`).subscribe(data => {
      this.institutes = data.sort((a, b) => a.name.localeCompare(b.name, 'ru'));
    });
  }

  openInstitute(institute: any) {
    this.router.navigate(['/institutes', institute.id]);
  }
}