import { createAction, props } from '@ngrx/store'
import {AddWorkerRequest, UpdateWorkerRequest, WorkerDetails} from "@app/workers/workers.model";
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
  DELETE_WORKER_FAILURE_ACTION = "[Workers] Error while deleting worker",
  GET_WORKER_ACTION = "[Workers] Get worker",
  GET_WORKER_SUCCESS_ACTION = "[Workers] Got worker successfully",
  GET_WORKER_FAILURE_ACTION = "[Workers] Error while getting worker",
  EDIT_WORKER_ACTION = "[Workers] Edit worker",
  EDIT_WORKER_SUCCESS_ACTION = "[Workers] Worker updated successfully",
  EDIT_WORKER_FAILURE_ACTION = "[Workers] Error while updating worker"
}

export const addWorkerAction = createAction(WorkersActions.ADD_WORKER_ACTION, props<{ request: AddWorkerRequest}>());
export const addWorkerSuccessAction = createAction(WorkersActions.ADD_WORKER_SUCCESS_ACTION, props<{ response: ResourceCreated}>());
export const addWorkerFailureAction = createAction(WorkersActions.ADD_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const deleteWorkerAction = createAction(WorkersActions.DELETE_WORKER_ACTION, props<{ workerId: number}>());
export const deleteWorkerSuccessAction = createAction(WorkersActions.DELETE_WORKER_SUCCESS_ACTION);
export const deleteWorkerFailureAction = createAction(WorkersActions.DELETE_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const getWorkerAction = createAction(WorkersActions.GET_WORKER_ACTION, props<{ workerId: number}>());
export const getWorkerSuccessAction = createAction(WorkersActions.GET_WORKER_SUCCESS_ACTION, props<{ response: WorkerDetails}>());
export const getWorkerFailureAction = createAction(WorkersActions.GET_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const editWorkerAction = createAction(WorkersActions.EDIT_WORKER_ACTION, props<{ workerId: number, request: UpdateWorkerRequest}>());
export const editWorkerSuccessAction = createAction(WorkersActions.EDIT_WORKER_SUCCESS_ACTION);
export const editWorkerFailureAction = createAction(WorkersActions.EDIT_WORKER_FAILURE_ACTION, props<{ error: ApiError }>());

export const getWorkersAction = createAction(WorkersActions.GET_WORKERS_ACTION, props<{ request: PageRequest, phrase: string}>());
export const getWorkersSuccessAction = createAction(WorkersActions.GET_WORKERS_SUCCESS_ACTION, props<{ response: PageResponse<WorkerDetails>}>());
export const getWorkersFailureAction = createAction(WorkersActions.GET_WORKERS_FAILURE_ACTION, props<{ error: ApiError}>());


