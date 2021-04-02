import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import {State} from "@app/workers/ngrx/workers.reducer";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import * as workersSelectors from '@app/workers/ngrx/workers.selectors';
import {AddWorkerRequest, UpdateWorkerRequest} from "@app/workers/workers.model";

@Component({
  selector: 'app-edit-worker',
  templateUrl: './edit-worker.component.html',
  styleUrls: ['./edit-worker.component.scss']
})
export class EditWorkerComponent implements OnInit {

  public static create(dialog: MatDialog, workerId: number): MatDialogRef<EditWorkerComponent> {
    return dialog.open(EditWorkerComponent, { width: '400px', data: { workerId: workerId }});
  }

  editWorkerForm = this.fb.group({
    fullName: ['', [Validators.required, Validators.maxLength(255)]],
    email: ['', [Validators.required, Validators.email]]
  });

  constructor(private fb: FormBuilder,
              private store: Store<State>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<EditWorkerComponent>) { }

  ngOnInit() {
    this.store.select(workersSelectors.selectWorker).subscribe(worker => {
      if (worker) {
        this.editWorkerForm.patchValue({
          fullName: worker.fullName,
          email: worker.email
        });
      }
    });
    this.store.dispatch(workersActions.getWorkerAction({ workerId: this.data.workerId }))
  }

  submit() {
    if (this.editWorkerForm.valid) {
      const fullName = this.editWorkerForm.value.fullName.trim();
      const email = this.editWorkerForm.value.email.trim();
      this.store.dispatch(workersActions.editWorkerAction({
        workerId: this.data.workerId,
        request: new UpdateWorkerRequest(fullName, email)
      }));
      this.dialogRef.close();
    } else {
      this.editWorkerForm.markAllAsTouched();
    }
  }
}
