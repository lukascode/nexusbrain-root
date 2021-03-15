import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AddWorkerRequest, UpdateWorkerRequest, WorkerDetails} from '@app/workers/workers.model';
import {ResourceCreated} from '@app/shared/model/resource-created';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkersService {

  basePath = '/nb/v1.0/workers';

  constructor(private httpClient: HttpClient) { }

  addWorker(request: AddWorkerRequest): Observable<ResourceCreated> {
    return this.httpClient.post<ResourceCreated>(`${this.basePath}/add`, request);
  }

  updateWorker(workerId: number, request: UpdateWorkerRequest): Observable<void> {
    return this.httpClient.put<void>(`${this.basePath}/${workerId}/update`, request);
  }

  getWorker(workerId: number): Observable<WorkerDetails> {
    return this.httpClient.get<WorkerDetails>(`${this.basePath}/${workerId}/get`);
  }

  deleteWorker(workerId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.basePath}/${workerId}/delete`);
  }
}
