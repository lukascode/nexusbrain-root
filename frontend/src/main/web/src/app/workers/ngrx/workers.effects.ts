import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {WorkersService} from "@app/workers/workers.service";
import * as WorkersActions from "@app/workers/ngrx/workers.actions";
import * as WorkersSelectors from '@app/workers/ngrx/workers.selectors';
import {catchError, map, switchMap, withLatestFrom} from "rxjs/operators";
import {of} from 'rxjs';
import {State} from "@app/workers/ngrx/workers.reducer";
import {Store} from "@ngrx/store";

@Injectable()
export class WorkersEffects {

  constructor(private actions$: Actions,
              private workersService: WorkersService, private store: Store<State>) {
  }

  addWorker$ = createEffect(() => this.actions$.pipe(
    ofType(WorkersActions.addWorkerAction),
    switchMap(({ request }) => this.workersService.addWorker(request).pipe(
      withLatestFrom(
        this.store.select(WorkersSelectors.selectPageRequest),
        this.store.select(WorkersSelectors.selectSearchPhrase)
      ),
      switchMap(([response, latestPageRequest, latestSearchPhrase]) => [
        WorkersActions.addWorkerSuccessAction({ response }),
        WorkersActions.getWorkersAction({ request: latestPageRequest, phrase: latestSearchPhrase })
      ]),
      catchError(error => of(WorkersActions.addWorkerFailureAction({ error: error.error })))
    ))
  ))

  deleteWorker$ = createEffect(() => this.actions$.pipe(
    ofType(WorkersActions.deleteWorkerAction),
    switchMap(({ workerId }) => this.workersService.deleteWorker(workerId).pipe(
      withLatestFrom(
        this.store.select(WorkersSelectors.selectPageRequest),
        this.store.select(WorkersSelectors.selectSearchPhrase)
      ),
      switchMap(([response, latestPageRequest, latestSearchPhrase]) => [
        WorkersActions.deleteWorkerSuccessAction(),
        WorkersActions.getWorkersAction({ request: latestPageRequest, phrase: latestSearchPhrase })
      ]),
      catchError(error => of(WorkersActions.deleteWorkerFailureAction({ error: error.error })))
    ))
  ))

  getWorkers$ = createEffect(() => this.actions$.pipe(
    ofType(WorkersActions.getWorkersAction),
    switchMap(({ request, phrase }) => this.workersService.getWorkers(request, phrase).pipe(
      map(response => WorkersActions.getWorkersSuccessAction({ response })),
      catchError(error => of(WorkersActions.getWorkersFailureAction({ error: error.error })))
    ))
  ))

}
