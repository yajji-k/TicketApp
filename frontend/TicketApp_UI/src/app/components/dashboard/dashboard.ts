import { Component, inject } from '@angular/core';
import { CreateTicket } from '../tickets/create-ticket/create-ticket';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-dashboard',
  imports: [
    MatButtonModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  dialog = inject(MatDialog) 

  openDialog(){
    this.dialog.open(CreateTicket, {
      height: '35rem',
      width: '40rem'
    })
  }
}
