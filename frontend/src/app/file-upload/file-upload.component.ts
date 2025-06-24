import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { environment } from '../../environments/environment';

interface FileUpload {
    id: number;
    originalFilename: string;
    uploadTimestamp: string;
    comment: string;
    selectedDate: string;
}

@Component({
    selector: 'app-file-upload',
    templateUrl: './file-upload.component.html',
    styleUrls: ['./file-upload.component.css'],
    standalone: true,
    imports: [CommonModule, FormsModule],
})
export class FileUploadComponent implements OnInit {
    files: FileUpload[] = [];
    selectedFile: File | null = null;
    comment: string = '';
    selectedDate: string = '';
    loading: boolean = false;
    private apiUrl = environment.apiUrl;

    password = '';
    accessGranted = false;
    error = false;

    private readonly correctPassword = '1234'; // Задайте нужный пароль

    constructor(private http: HttpClient) { }

    ngOnInit() {
        this.loadFiles();
    }

    onFileSelected(event: any) {
        this.selectedFile = event.target.files[0];
    }

    uploadFile() {
        if (this.selectedFile && this.selectedDate) {
            this.loading = true;
            const formData = new FormData();
            formData.append('file', this.selectedFile);
            formData.append('comment', this.comment);
            formData.append('selectedDate', this.selectedDate);

            this.http.post(`${this.apiUrl}/api/files/upload`, formData)
                .subscribe(() => {
                    this.loadFiles();
                    this.selectedFile = null;
                    this.comment = '';
                    this.selectedDate = '';
                    this.loading = false;
                }, error => {
                    console.error(error);
                    this.loading = false;
                });
        }
    }

    downloadFile(id: number) {
        window.open(`${this.apiUrl}/api/files/download/${id}`);
    }

    deleteFile(fileId: number) {
        if (confirm('Вы уверены, что хотите удалить файл?')) {
            this.http.delete(`${this.apiUrl}/api/files/${fileId}`).subscribe({
                next: () => {
                    this.loadFiles(); // обновить список файлов после удаления
                },
                error: err => {
                    alert('Ошибка при удалении файла');
                }
            });
        }
    }

    private loadFiles() {
        this.http.get<FileUpload[]>(`${this.apiUrl}/api/files`)
            .subscribe(files => this.files = files);
    }

    checkPassword() {
        if (this.password === this.correctPassword) {
            this.accessGranted = true;
            this.error = false;
        } else {
            this.error = true;
        }
    }
}