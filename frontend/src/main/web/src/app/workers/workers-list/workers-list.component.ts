import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {merge, Observable, of} from "rxjs";
import {WorkerDetails} from "@app/workers/workers.model";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {FormControl} from "@angular/forms";
import {Store} from "@ngrx/store";
import {State} from "@app/workers/ngrx/workers.reducer";
import {MatDialog} from "@angular/material/dialog";
import {debounceTime, delay, distinctUntilChanged, map, startWith, switchMap} from "rxjs/operators";
import * as workersSelectors from "@app/workers/ngrx/workers.selectors";
import {PageRequest} from "@app/shared/model/page";
import * as workersActions from "@app/workers/ngrx/workers.actions";
import {EditWorkerComponent} from "@app/workers/edit-worker/edit-worker.component";
import {AlertDialogComponent} from "@app/shared/components/alert-dialog/alert-dialog.component";

@Component({
  selector: 'app-workers-list',
  templateUrl: './workers-list.component.html',
  styleUrls: ['./workers-list.component.scss']
})
export class WorkersListComponent implements AfterViewInit {
  displayedColumns: string[] = ['avatar', 'fullName', 'email', 'numberOfTeams', 'actions'];
  pageSizeOptions: number[] = [5, 10, 25, 100];
  dataSource: Observable<WorkerDetails[]>;
  totalElements: Observable<number>;
  isLoading: Observable<boolean>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  searchInput = new FormControl();
  searchInputObservable: Observable<String>;

  constructor(private store: Store<State>, private dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this.searchInputObservable = this.searchInput.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      map(phrase => phrase.trim())
    );
    this.dataSource = this.store.select(workersSelectors.selectWorkers);
    this.totalElements = this.store.select(workersSelectors.selectTotalElements);
    this.isLoading = this.store.select(workersSelectors.selectLoading).pipe(
      switchMap(loading => {
        return !loading ? of(loading).pipe(delay(250)) : of(loading);
      })
    );
    merge(this.sort.sortChange, this.paginator.page, this.searchInputObservable).pipe(startWith({})).subscribe(() => {
      let pageRequest: PageRequest = new PageRequest(
        this.paginator.pageIndex,
        this.paginator.pageSize,
        this.sort.active,
        this.sort.direction
      );
      this.store.dispatch(workersActions.getWorkersAction({request: pageRequest, phrase: this.searchInput.value}));
    });
  }

  editWorker(workerId: number) {
    EditWorkerComponent.create(this.dialog, workerId);
  }

  deleteWorker(workerId: number) {
    AlertDialogComponent.WARN(this.dialog, "Ostrzeżenie", "Czy na pewno chcesz usunąć tego pracownika?")
      .subscribe(result => {
        if (result) {
          this.store.dispatch(workersActions.deleteWorkerAction({ workerId }))
        }
      });
  }
}
