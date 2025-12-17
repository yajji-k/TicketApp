import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { env } from '../../env/environment';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { error } from 'node:console';
import { MatDialog } from '@angular/material/dialog';
import { Forcelogoutdialogue } from '../../components/dialogue/forcelogoutdialogue/forcelogoutdialogue';

export interface LoginRequest {
  username: string;
  password: string;
  forceLogin?: boolean;
}

export interface LoginResponse {
  accessToken: string,
  expiresAt: string,
  roles: string[],
  tokenType: string
}

export interface LoginErrorResponse {
  error: string;
  message: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  accessToken: string = '';
  roles: string[] = [];
  tokenType: string = '';

  private readonly authBaseUrl = env.authms;

  constructor(private http: HttpClient, private router: Router, private dialog: MatDialog) { }

  public postLogin(body: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.authBaseUrl}/auth/login`, body);
  }

  public login(payload: LoginRequest): void {
    this.postLogin(payload).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },

      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {

          const errBody = error.error as LoginErrorResponse;

          const dialogRef = this.dialog.open(Forcelogoutdialogue, {
            width: '420px',
            data: {
              message: errBody.message
            }
          });

          dialogRef.afterClosed().subscribe((forceLogin: boolean) => {
            if (forceLogin) {
              const forcePayload: LoginRequest = {
                ...payload,
                forceLogin: true
              };

              this.postLogin(forcePayload).subscribe({
                next: () => this.router.navigate(['/dashboard']),
                error: err => console.error('Force login failed', err)
              });
            }
          });

          return;
        }
      }
    });
  }

}
