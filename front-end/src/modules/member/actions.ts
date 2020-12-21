import { createAsyncAction } from "typesafe-actions";
import { Member } from '../../api/member';
import { AxiosError } from 'axios';

export const GET_MEMBER = 'member/GET_MEMBER';
export const GET_MEMBER_SUCCESS = 'member/GET_MEMBER_SUCCESS';
export const GET_MEMBER_ERROR = 'member/GET_MEMBER_ERROR';

export const getMemberAsync = createAsyncAction(
    GET_MEMBER,
    GET_MEMBER_SUCCESS,
    GET_MEMBER_ERROR
)<undefined, Member, AxiosError>();