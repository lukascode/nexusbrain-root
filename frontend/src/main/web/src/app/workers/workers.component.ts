import { Component, OnInit } from '@angular/core';
import {WorkersService} from "@app/workers/workers.service";

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss']
})
export class WorkersComponent implements OnInit {

  constructor(private workersService: WorkersService) { }

  ngOnInit(): void {
  }

  addWorker() {
    this.workersService.deleteWorker(1).subscribe(() => console.log("success"));
  }

}
