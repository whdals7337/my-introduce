import { createReducer } from 'typesafe-actions';
import { asyncState, createAsyncReducer } from '../../lib/reducerUtils';
import { getMemberAsync } from './actions';
import { MemberAction, MemberState } from './types';

const initalState: MemberState = {
    member: asyncState.initial()
};

const member = createReducer<MemberState, MemberAction>(
    initalState
).handleAction(
    [
        getMemberAsync.request,
        getMemberAsync.success,
        getMemberAsync.failure
    ],
    createAsyncReducer(getMemberAsync, 'member')
);

export default member;