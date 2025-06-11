import { NgFor } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NgFor, HttpClientModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected title = 'frontend';
  institutes: { id: number; name: string }[] = [];
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<{ id: number; name: string }[]>(
      `${this.apiUrl}/api/institutes`
    ).subscribe(data => {
      this.institutes = data;
    });
  }
}
