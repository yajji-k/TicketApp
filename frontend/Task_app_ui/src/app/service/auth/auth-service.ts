import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { env } from '../../env/environment';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

export interface LoginRequest {
  username: string,
  password: string
}

export interface LoginResponse {
  accessToken: string,
  expiresAt: string,
  roles: string[],
  tokenType: string
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  accessToken: string = '';
  roles: string[] = [];
  tokenType: string = '';


  private readonly authBaseUrl = env.authms;


  constructor(private http: HttpClient, private router: Router) { }

  public postLogin(body: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.authBaseUrl}/auth/login`, body);
  }

  public login(payload: LoginRequest) {
    this.postLogin(payload).subscribe((response:LoginResponse)=>{
      this.accessToken = response.accessToken;
      this.roles = response.roles;
      this.tokenType = response.tokenType;
      this.router.navigate(['/dashboard']);
    });
  }

}
