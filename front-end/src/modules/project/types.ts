import * as actions from './actions';
import { ActionType } from 'typesafe-actions';
import { Project } from '../../api/project';
import { AsyncState } from '../../lib/reducerUtils';

export type ProjectAction = ActionType<typeof actions>;
export type ProjectState = {
    project: AsyncState<Project[], Error>;
};