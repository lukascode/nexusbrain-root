import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, Validators} from '@angular/forms';
import {Store} from "@ngrx/store";
import {State} from "@app/workers/ngrx/workers.reducer";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import {AddWorkerRequest} from "@app/workers/workers.model";
import {Actions, ofType} from "@ngrx/effects";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-add-worker',
  templateUrl: './add-worker.component.html',
  styleUrls: ['./add-worker.component.scss']
})
export class AddWorkerComponent implements OnInit, OnDestroy {

  public static create(dialog: MatDialog): MatDialogRef<AddWorkerComponent> {
    return dialog.open(AddWorkerComponent, { width: '400px'});
  }

  addWorkerForm = this.fb.group({
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
              public dialogRef: MatDialogRef<AddWorkerComponent>) {

  }

  ngOnInit() {
    this.successSub = this.actions$.pipe(
      ofType(workersActions.addWorkerSuccessAction)
    ).subscribe(() => {
      this.dialogRef.close();
    });
    this.failureSub = this.actions$.pipe(
      ofType(workersActions.addWorkerFailureAction)
    ).subscribe(({ error }) => {
      if (error.message === 'EMAIL_ALREADY_EXISTS') {
        this.backendError = true;
        this.backendErrorMessage = 'Error: Given address email already exists in the database';
      }
    });
    this.formChanges = this.addWorkerForm.valueChanges.subscribe(() => {
      this.backendError = false;
    });
  }

  submit() {
    if (this.addWorkerForm.valid && !this.backendError) {
      const fullName = this.addWorkerForm.value.fullName.trim();
      const email = this.addWorkerForm.value.email.trim();
      this.store.dispatch(workersActions.addWorkerAction({ request: new AddWorkerRequest(fullName, email)}));
    } else {
      this.addWorkerForm.markAllAsTouched();
    }
  }

  ngOnDestroy() {
    this.successSub.unsubscribe();
    this.failureSub.unsubscribe();
    this.formChanges.unsubscribe();
  }
}
