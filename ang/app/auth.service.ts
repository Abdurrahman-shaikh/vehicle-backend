import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/admin';
  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    return this.http.post(`${this.baseUrl}/login`, { username, password }, { responseType: 'text' });
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }


// auth.service.ts
getAllUnderwriters() {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

  return this.http.get(`http://localhost:8080/api/underwriter/underwriters`, {
    headers,
    withCredentials: true  // Add this option
  });
}}
