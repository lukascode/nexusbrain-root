import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkersComponent } from '@app/workers/workers.component';
import {WorkersRoutingModule} from '@app/workers/workers-routing.module';
import {SharedModule} from "@app/shared/shared.module";
import * as workersReducer from '@app/workers/ngrx/workers.reducer';
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {WorkersEffects} from "@app/workers/ngrx/workers.effects";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [WorkersComponent],
  imports: [
    CommonModule,
    WorkersRoutingModule,
    SharedModule,
    StoreModule.forFeature(workersReducer.workersFeatureKey, workersReducer.workersReducer),
    EffectsModule.forFeature([WorkersEffects]),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class WorkersModule { }
