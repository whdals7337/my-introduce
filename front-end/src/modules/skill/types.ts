import * as actions from './actions';
import { ActionType } from 'typesafe-actions';
import { Skill } from '../../api/skill';
import { AsyncState } from '../../lib/reducerUtils';

export type SkillAction = ActionType<typeof actions>;
export type SkillState = {
    skill: AsyncState<Skill[], Error>;
};