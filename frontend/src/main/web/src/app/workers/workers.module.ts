import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkersComponent } from '@app/workers/workers.component';
import {WorkersRoutingModule} from '@app/workers/workers-routing.module';
import {SharedModule} from "@app/shared/shared.module";



@NgModule({
  declarations: [WorkersComponent],
  imports: [
    CommonModule,
    WorkersRoutingModule,
    SharedModule
  ]
})
export class WorkersModule { }
