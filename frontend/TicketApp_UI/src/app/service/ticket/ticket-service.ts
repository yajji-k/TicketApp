import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { env } from '../../env/environment';

export interface CreateTicketRequest {
  title: string;
  description: string;
}

export interface TicketResponse {
  id: number;
  title: string;
  description: string;
  status: string;
  createdBy: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root',
})
export class TicketService {

  private readonly ticketBaseUrl = `${env.gateway}/ticket-service`;

  constructor(private http: HttpClient) {}

  createTicket(payload: CreateTicketRequest): Observable<TicketResponse> {
    return this.http.post<TicketResponse>(this.ticketBaseUrl+'/tickets/create', payload);
  }
}
