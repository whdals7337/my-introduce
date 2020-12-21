import { createReducer } from 'typesafe-actions';
import { asyncState, createAsyncReducer } from '../../lib/reducerUtils';
import { getProjectByMemberIdAsync } from './actions';
import { ProjectAction, ProjectState } from './types';

const initalState: ProjectState = {
    project: asyncState.initial()
};

const project = createReducer<ProjectState, ProjectAction>(
    initalState
).handleAction(
    [
        getProjectByMemberIdAsync.request,
        getProjectByMemberIdAsync.success,
        getProjectByMemberIdAsync.failure
    ],
    createAsyncReducer(getProjectByMemberIdAsync, 'project')
);

export default project;