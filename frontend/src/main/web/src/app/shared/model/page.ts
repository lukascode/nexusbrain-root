export class PageRequest {
  page: number;
  size: number;
  sort: string;

  constructor(page: number = 0, size: number = 25, sortColumn: string = '', sortDirection: string = '') {
    this.page = page;
    this.size = size;
    this.setSort(sortColumn, sortDirection);
  }

  setSort(column: string, direction: string) {
    if (column && direction) {
      this.sort = `${column},${direction}`;
    }
  }
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  numberOfElements: number;
}
