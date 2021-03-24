import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MaterialModule} from "@app/shared/material/material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import { AlertDialogComponent } from './components/alert-dialog/alert-dialog.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

@NgModule({
  declarations: [AlertDialogComponent, NotFoundComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule
  ],
  exports: [
    MaterialModule,
    FlexLayoutModule
  ]
})
export class SharedModule { }
