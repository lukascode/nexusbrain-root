import {createFeatureSelector, createSelector} from "@ngrx/store";
import {State, workersFeatureKey} from "@app/workers/ngrx/workers.reducer";


export const selectWorkersState = createFeatureSelector<State>(workersFeatureKey);

export const selectLoading = createSelector(
  selectWorkersState,
  (state: State) => state.loading
);

export const selectError = createSelector(
  selectWorkersState,
  (state: State) => state.error
);

export const selectWorkerCreated = createSelector(
  selectWorkersState,
  (state: State) => state.workerCreated
);

export const selectWorkers = createSelector(
  selectWorkersState,
  (state: State) => state.workers ? state.workers.content : null
);

export const selectTotalElements = createSelector(
  selectWorkersState,
  (state: State) => state.workers ? state.workers.totalElements : 0
);

export const selectPageRequest = createSelector(
  selectWorkersState,
  (state: State) => state.pageRequest
);

export const selectSearchPhrase = createSelector(
  selectWorkersState,
  (state: State) => state.searchPhrase
);

export const selectWorker = createSelector(
  selectWorkersState,
  (state: State) => state.worker
);
