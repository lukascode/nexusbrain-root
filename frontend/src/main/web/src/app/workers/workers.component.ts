import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Store} from "@ngrx/store";
import * as workersActions from '@app/workers/ngrx/workers.actions';
import * as workersSelectors from '@app/workers/ngrx/workers.selectors';
import {State} from '@app/workers/ngrx/workers.reducer';
import {AddWorkerRequest, WorkerDetails} from "@app/workers/workers.model";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {merge, Observable} from "rxjs";
import {PageRequest} from "@app/shared/model/page";
import {startWith} from "rxjs/operators";

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss']
})
export class WorkersComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['fullName', 'email', 'numberOfTeams'];
  pageSizeOptions: number[] = [5, 10, 25, 100];
  dataSource: Observable<WorkerDetails[]>;
  totalElements: Observable<number>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private store: Store<State>) {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.dataSource = this.store.select(workersSelectors.selectWorkers);
    this.totalElements = this.store.select(workersSelectors.selectTotalElements);
    merge(this.sort.sortChange, this.paginator.page).pipe(startWith({})).subscribe(() => {
      let pageRequest: PageRequest = new PageRequest(
        this.paginator.pageIndex,
        this.paginator.pageSize,
        this.sort.active,
        this.sort.direction
      );
      this.store.dispatch(workersActions.getWorkersAction({ request: pageRequest }));
    });
  }

  addWorker() {
    const rq: AddWorkerRequest = new AddWorkerRequest("Test3", "ls3@example.com");
    this.store.dispatch(workersActions.addWorkerAction({ request: rq }));
  }

}
