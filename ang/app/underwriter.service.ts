import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class UnderwriterService {
  private baseUrl = 'http://localhost:8080/api/underwriter';

  constructor(private http: HttpClient) {}

  getAllUnderwriters() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.baseUrl}/underwriters`, { headers });
  }
}

