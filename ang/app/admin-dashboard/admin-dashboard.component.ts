import { Component, OnInit } from '@angular/core';
import { UnderwriterService } from '../underwriter.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  underwriters: any[] = [];
  errorMessage = '';

  constructor(private underwriterService: UnderwriterService) {}

  ngOnInit() {
    const token = localStorage.getItem('token');

    if (token) {
      this.underwriterService.getAllUnderwriters().subscribe({
        next: (data: any) => {
          this.underwriters = data;
        },
        error: (err) => {
          console.error('Unauthorized or error:', err);
          this.errorMessage = 'Session expired or unauthorized access.';
        }
      });
    } else {
      this.errorMessage = 'No token found. Please login.';
    }
  }
}

