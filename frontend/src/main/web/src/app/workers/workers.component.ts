import {Component} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddWorkerComponent} from "@app/workers/add-worker/add-worker.component";

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss']
})
export class WorkersComponent {

  constructor(private dialog: MatDialog) {
  }

  addWorker() {
    AddWorkerComponent.create(this.dialog);
  }
}
