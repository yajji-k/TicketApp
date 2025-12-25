import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth/auth-service';

const IGNORED_URLS: string[] = [
  '/auth-service/login',
];

@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    if (this.isIgnoredUrl(req.url)) {
      return next.handle(req);
    }

    let headers = req.headers
      .set('Content-Type', 'application/json');

    const token = this.authService.getToken?.();

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    const modifiedRequest = req.clone({ headers });

    return next.handle(modifiedRequest);
  }

  private isIgnoredUrl(url: string): boolean {
    return IGNORED_URLS.some(ignored => url.includes(ignored));
  }
}
