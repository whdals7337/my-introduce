import * as actions from './actions';
import { ActionType } from 'typesafe-actions';
import { Member } from '../../api/member';
import { AsyncState } from '../../lib/reducerUtils';
import {Header} from '../../api/Header';

export type MemberAction = ActionType<typeof actions>;
export type MemberState = {
    member: AsyncState<Header<Member>, Error>;
};