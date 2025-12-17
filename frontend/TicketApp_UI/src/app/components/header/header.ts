import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../service/auth/auth-service';

@Component({
  selector: 'app-header',
  imports: [
    MatToolbarModule,
    MatButtonModule
  ],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  constructor(private authService: AuthService){}

  ngOnit() {
  }

  onLogout(){}
}
