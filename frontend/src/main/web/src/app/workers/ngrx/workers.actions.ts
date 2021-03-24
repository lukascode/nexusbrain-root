import { createAction, props } from '@ngrx/store'
import {AddWorkerRequest, WorkerDetails} from "@app/workers/workers.model";
import { ResourceCreated } from '@app/shared/model/resource-created';
import {ApiError} from "@app/shared/model/api-error";
import {PageRequest, PageResponse} from "@app/shared/model/page";

export enum WorkersActions {
  ADD_WORKER_ACTION = "[Workers] Add worker",
  ADD_WORKER_SUCCESS_ACTION = "[Workers] Worker added successfully",
  ADD_WORKER_FAILURE_ACTION = "[Workers] Error while adding worker",
  GET_WORKERS_ACTION = "[Workers] Get workers",
  GET_WORKERS_SUCCESS_ACTION = "[Workers] Got workers successfully",
  GET_WORKERS_FAILURE_ACTION = "[Workers] Error while getting workers",
  DELETE_WORKER_ACTION = "[Workers] Delete worker",
  DELETE_WORKER_SUCCESS_ACTION = "[Workers] Worker deleted successfully",
  DELETE_WORKER_FAILURE_ACTION = "[Workers] Error while deleting worker"
}

export const addWorkerAction = createAction(WorkersActions.ADD_WORKER_ACTION, props<{ request: AddWorkerRequest}>());
export const addWorkerSuccessAction = createAction(WorkersActions.ADD_WORKER_SUCCESS_ACTION, props<{ response: ResourceCreated}>());
export const addWorkerFailureAction = createAction(WorkersActions.ADD_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const deleteWorkerAction = createAction(WorkersActions.DELETE_WORKER_ACTION, props<{ workerId: number}>());
export const deleteWorkerSuccessAction = createAction(WorkersActions.DELETE_WORKER_SUCCESS_ACTION);
export const deleteWorkerFailureAction = createAction(WorkersActions.DELETE_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const getWorkersAction = createAction(WorkersActions.GET_WORKERS_ACTION, props<{ request: PageRequest, phrase: string}>());
export const getWorkersSuccessAction = createAction(WorkersActions.GET_WORKERS_SUCCESS_ACTION, props<{ response: PageResponse<WorkerDetails>}>());
export const getWorkersFailureAction = createAction(WorkersActions.GET_WORKERS_FAILURE_ACTION, props<{ error: ApiError}>());
