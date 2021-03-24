import {createReducer, on} from '@ngrx/store';
import {
  addWorkerAction,
  addWorkerFailureAction,
  addWorkerSuccessAction, deleteWorkerFailureAction,
  getWorkersAction, getWorkersFailureAction,
  getWorkersSuccessAction
} from '@app/workers/ngrx/workers.actions';
import {ApiError} from "@app/shared/model/api-error";
import {ResourceCreated} from "@app/shared/model/resource-created";
import {WorkerDetails} from "@app/workers/workers.model";
import {PageRequest, PageResponse} from "@app/shared/model/page";

export const workersFeatureKey = 'workers';

export interface State {
  loading: boolean;
  error: ApiError | any;
  workerCreated: ResourceCreated | any;
  workers: PageResponse<WorkerDetails> | any;
  pageRequest: PageRequest;
  searchPhrase: string;
};

export const initialState: State = {
  loading: false,
  error: null,
  workerCreated: null,
  workers: null,
  pageRequest: new PageRequest(),
  searchPhrase: ''
};

export const workersReducer = createReducer(
  initialState,
  on(addWorkerAction, state => ({
    ...state
  })),
  on(addWorkerSuccessAction, (state, { response }) => ({
    ...state,
    workerCreated: response
  })),
  on(addWorkerFailureAction, (state, { error }) => ({
    ...state,
    error: error
  })),
  on(deleteWorkerFailureAction, (state, { error }) => ({
    ...state,
    error: error
  })),
  on(getWorkersAction, (state, { request, phrase }) => ({
    ...state,
    loading: true,
    pageRequest: request,
    searchPhrase: phrase
  })),
  on(getWorkersSuccessAction, (state, { response }) => ({
    ...state,
    loading: false,
    workers: response
  })),
  on(getWorkersFailureAction, (state, { error }) => ({
    ...state,
    loading: false,
    error: error
  }))
)
