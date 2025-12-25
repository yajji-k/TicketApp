import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { env } from '../../env/environment';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
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

export interface RefreshTokenResponse {
  accessToken: string;
  expiresAt: string;
  tokenType: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  accessToken: string = '';
  roles: string[] = [];
  tokenType: string = '';

  private readonly authBaseUrl = env.gateway;

  constructor(private http: HttpClient, private router: Router, private dialog: MatDialog) { }

  public postLogin(body: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.authBaseUrl}/auth-service/login`, body);
  }

  public login(payload: LoginRequest): void {
    this.postLogin(payload).subscribe({
      next: (response: LoginResponse) => {
        this.accessToken = response.accessToken;
        this.roles = response.roles;
        this.tokenType = response.tokenType;
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
                next: (response: LoginResponse) => {
                  this.accessToken = response.accessToken;
                  this.roles = response.roles;
                  this.tokenType = response.tokenType;
                  this.router.navigate(['/dashboard']);
                },
                error: err => console.error('Force login failed', err)
              });
            }
          });

          return;
        }
      }
    });
  }

  refreshToken(): Observable<RefreshTokenResponse> {
    const token = this.accessToken;

    return this.http.post<RefreshTokenResponse>(
      `${this.authBaseUrl}/auth-service/refresh`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
  }

  getToken(){
    return this.accessToken;
  }

}
