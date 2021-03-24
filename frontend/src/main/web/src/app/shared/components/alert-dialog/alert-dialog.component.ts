import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-alert-dialog',
  templateUrl: './alert-dialog.component.html',
  styleUrls: ['./alert-dialog.component.scss']
})
export class AlertDialogComponent {

  title: string;
  message: string;
  type: string;

  constructor(
    public dialogRef: MatDialogRef<AlertDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this.title = data.title;
    this.message = data.message;
    this.type = data.type;
  }

  static INFO(dialog: MatDialog, title: string, message: string) {
    return this.create(dialog, title, message, 'info').afterClosed();
  }

  static ERROR(dialog: MatDialog, title: string, message: string) {
    return this.create(dialog, title, message, 'error').afterClosed();
  }

  static WARN(dialog: MatDialog, title: string, message: string) {
    return this.create(dialog, title, message, 'warn').afterClosed();
  }

  private static create(dialog: MatDialog, title: string, message: string, type: string): MatDialogRef<AlertDialogComponent> {
    return dialog.open(AlertDialogComponent, {
      maxWidth: '40vw',
      data: {
        title: title,
        message: message,
        type: type
      }
    });
  }
}
