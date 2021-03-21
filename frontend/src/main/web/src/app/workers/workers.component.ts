import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import * as workersSelectors from '@app/workers/ngrx/workers.selectors';
import {State} from '@app/workers/ngrx/workers.reducer';
import {AddWorkerRequest, WorkerDetails} from "@app/workers/workers.model";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {merge, Observable, of} from "rxjs";
import {PageRequest} from "@app/shared/model/page";
import {debounceTime, delay, distinctUntilChanged, map, startWith, switchMap} from "rxjs/operators";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss']
})
export class WorkersComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['avatar', 'fullName', 'email', 'numberOfTeams', 'actions'];
  pageSizeOptions: number[] = [5, 10, 25, 100];
  dataSource: Observable<WorkerDetails[]>;
  totalElements: Observable<number>;
  isLoading: Observable<boolean>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  searchInput = new FormControl();
  searchInputObservable: Observable<String>;

  constructor(private store: Store<State>) {
  }

  ngOnInit() {
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
      this.store.dispatch(workersActions.getWorkersAction({ request: pageRequest, phrase: this.searchInput.value }));
    });
  }

  addWorker() {
    const rq: AddWorkerRequest = new AddWorkerRequest("Sakowicz", "sakowicz@example.com");
    this.store.dispatch(workersActions.addWorkerAction({ request: rq }));
  }

}
