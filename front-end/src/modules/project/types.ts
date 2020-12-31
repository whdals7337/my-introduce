import * as actions from './actions';
import { ActionType } from 'typesafe-actions';
import { Project } from '../../api/project';
import { AsyncState } from '../../lib/reducerUtils';
import {Header} from '../../api/Header';

export type ProjectAction = ActionType<typeof actions>;
export type ProjectState = {
    project: AsyncState<Header<Project[]>, Error>;
};