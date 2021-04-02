import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, Validators} from '@angular/forms';
import {Store} from "@ngrx/store";
import {State} from "@app/workers/ngrx/workers.reducer";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import {AddWorkerRequest} from "@app/workers/workers.model";

@Component({
  selector: 'app-add-worker',
  templateUrl: './add-worker.component.html',
  styleUrls: ['./add-worker.component.scss']
})
export class AddWorkerComponent {

  public static create(dialog: MatDialog): MatDialogRef<AddWorkerComponent> {
    return dialog.open(AddWorkerComponent, { width: '400px'});
  }

  addWorkerForm = this.fb.group({
    fullName: ['', [Validators.required, Validators.maxLength(255)]],
    email: ['', [Validators.required, Validators.email]]
  });

  constructor(private fb: FormBuilder,
              private store: Store<State>,
              public dialogRef: MatDialogRef<AddWorkerComponent>) {

  }

  submit() {
    if (this.addWorkerForm.valid) {
      const fullName = this.addWorkerForm.value.fullName.trim();
      const email = this.addWorkerForm.value.email.trim();
      this.store.dispatch(workersActions.addWorkerAction({ request: new AddWorkerRequest(fullName, email)}));
      this.dialogRef.close();
    } else {
      this.addWorkerForm.markAllAsTouched();
    }
  }
}
