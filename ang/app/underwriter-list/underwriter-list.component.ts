import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-underwriter-list',
  templateUrl: './underwriter-list.component.html'
})
export class UnderwriterListComponent {
  underwriters: any[] = [];
  showList = false;
  error = '';

  constructor(private authService: AuthService) {}

  loadUnderwriters() {
    this.authService.getAllUnderwriters().subscribe({
      next: (data: any) => {
        this.underwriters = data;
        this.showList = true;
      },
      error: () => {
        this.error = 'Unauthorized or failed to load underwriters';
      }
    });
  }
}

