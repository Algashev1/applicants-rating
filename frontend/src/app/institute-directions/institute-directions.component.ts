import { CommonModule, Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
    selector: 'app-institute-directions',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './institute-directions.component.html',
    styleUrls: ['./institute-directions.component.css']
})
export class InstituteDirectionsComponent implements OnInit {
    directions: any[] = [];
    instituteName: string = '';
    private apiUrl = environment.apiUrl;
    showChanges: boolean = true;

    constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router, private location: Location) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        this.http.get<any>(`${this.apiUrl}/api/institutes/${id}/directions-with-stats`)
            .subscribe(data => {
                this.directions = data.directions;
                this.instituteName = data.instituteName;
                this.directions = this.directions.sort((a, b) => a.name.localeCompare(b.name));
            });
    }

    openDirection(dir: any) {
        this.router.navigate(['/directions', dir.name]);
    }

    goBack() {
        this.router.navigate(['/']);
    }

    toggleShowChanges(event: Event): void {
        this.showChanges = (event.target as HTMLInputElement).checked;
    }
}