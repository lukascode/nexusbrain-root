import { Injectable } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class MatPaginatorIntlCustom extends MatPaginatorIntl {
  itemsPerPageLabel: string;
  nextPageLabel: string;
  previousPageLabel: string;

  constructor(private translate: TranslateService) {
    super();
    this.translate.onLangChange.subscribe(() => {
      this.translateLabels();
    });
    this.translateLabels();
  }

  getRangeLabel = function (this: MatPaginatorIntlCustom, page: number, pageSize: number, length: number) {
    const of = this.translate ? this.translate.instant('paginator.of') : 'of';
    if (length === 0 || pageSize === 0) {
      return '0 ' + of + ' ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' ' + of + ' ' + length;
  };

  translateLabels() {
    this.itemsPerPageLabel = this.translate.instant('paginator.itemsPerPageLabel');
    this.nextPageLabel = this.translate.instant('paginator.nextPageLabel');
    this.previousPageLabel = this.translate.instant('paginator.previousPageLabel');
  }
}
