import { createReducer } from 'typesafe-actions';
import { asyncState, createAsyncReducer } from '../../lib/reducerUtils';
import { getSkillsByMemberIdAsync } from './actions';
import { SkillAction, SkillState } from './types';

const initalState: SkillState = {
    skill: asyncState.initial()
};

const skill = createReducer<SkillState, SkillAction>(
    initalState
).handleAction(
    [
        getSkillsByMemberIdAsync.request,
        getSkillsByMemberIdAsync.success,
        getSkillsByMemberIdAsync.failure
    ],
    createAsyncReducer(getSkillsByMemberIdAsync, 'skill')
);

export default skill;