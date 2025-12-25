import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { CreateTicketRequest, TicketResponse, TicketService } from '../../../service/ticket/ticket-service';

@Component({
  selector: 'app-create-ticket',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './create-ticket.html',
  styleUrl: './create-ticket.css',
})
export class CreateTicket {

  ticketForm: FormGroup;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService
  ) {
    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid || this.isSubmitting) {
      return;
    }

    const payload: CreateTicketRequest = this.ticketForm.value;
    this.isSubmitting = true;

    this.ticketService.createTicket(payload).subscribe({
      next: (response: TicketResponse) => {
        console.log('Ticket created successfully:', response);
        this.ticketForm.reset();
        this.isSubmitting = false;
      },
      error: (error: unknown) => {
        console.error('Failed to create ticket:', error);
        this.isSubmitting = false;
      }
    });

  }
}
