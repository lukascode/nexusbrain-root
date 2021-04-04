import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Store} from "@ngrx/store";
import {State} from "@app/workers/ngrx/workers.reducer";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import * as workersSelectors from '@app/workers/ngrx/workers.selectors';
import {UpdateWorkerRequest} from "@app/workers/workers.model";
import {Actions, ofType} from "@ngrx/effects";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-edit-worker',
  templateUrl: './edit-worker.component.html',
  styleUrls: ['./edit-worker.component.scss']
})
export class EditWorkerComponent implements OnInit, OnDestroy {

  public static create(dialog: MatDialog, workerId: number): MatDialogRef<EditWorkerComponent> {
    return dialog.open(EditWorkerComponent, { width: '400px', data: { workerId: workerId }});
  }

  editWorkerForm = this.fb.group({
    fullName: ['', [Validators.required, Validators.maxLength(255)]],
    email: ['', [Validators.required, Validators.email]]
  });

  successSub: Subscription;
  failureSub: Subscription;
  formChanges: Subscription;

  backendError: boolean = false;
  backendErrorMessage: string = '';

  constructor(private fb: FormBuilder,
              private store: Store<State>,
              private actions$: Actions,
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
    this.successSub = this.actions$.pipe(
      ofType(workersActions.editWorkerSuccessAction)
    ).subscribe(() => {
      this.dialogRef.close();
    });
    this.failureSub = this.actions$.pipe(
      ofType(workersActions.editWorkerFailureAction)
    ).subscribe(({ error }) => {
      if (error.message === 'EMAIL_ALREADY_EXISTS') {
        this.backendError = true;
        this.backendErrorMessage = 'Error: Given address email already exists in the database';
      }
    });
    this.formChanges = this.editWorkerForm.valueChanges.subscribe(() => {
      this.backendError = false;
    });
    this.store.dispatch(workersActions.getWorkerAction({ workerId: this.data.workerId }))
  }

  submit() {
    if (this.editWorkerForm.valid && !this.backendError) {
      const fullName = this.editWorkerForm.value.fullName.trim();
      const email = this.editWorkerForm.value.email.trim();
      this.store.dispatch(workersActions.editWorkerAction({
        workerId: this.data.workerId,
        request: new UpdateWorkerRequest(fullName, email)
      }));
    } else {
      this.editWorkerForm.markAllAsTouched();
    }
  }

  ngOnDestroy() {
    this.successSub.unsubscribe();
    this.failureSub.unsubscribe();
    this.formChanges.unsubscribe();
  }
}
