import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-forcelogoutdialogue',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './forcelogoutdialogue.html',
  styleUrl: './forcelogoutdialogue.css'
})
export class Forcelogoutdialogue {

  constructor(
    private dialogRef: MatDialogRef<Forcelogoutdialogue>,
    @Inject(MAT_DIALOG_DATA)
    public data: { message: string }
  ) {}

  onForceLogout(): void {
    this.dialogRef.close(true); // user confirmed
  }

  onCancel(): void {
    this.dialogRef.close(false); // user cancelled
  }
}
