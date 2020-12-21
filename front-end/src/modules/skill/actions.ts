import { createAsyncAction } from "typesafe-actions";
import { Skill } from '../../api/skill';
import { AxiosError } from 'axios';

export const GET_SKILLS_BYMEMBERID = 'skill/GET_SKILLS_BYMEMBERID';
export const GET_SKILLS_BYMEMBERID_SUCCESS = 'skill/GET_SKILLS_BYMEMBERID_SUCCESS';
export const GET_SKILLS_BYMEMBERID_ERROR = 'skill/GET_SKILLS_BYMEMBERID_ERROR';

export const getSkillsByMemberIdAsync = createAsyncAction(
    GET_SKILLS_BYMEMBERID,
    GET_SKILLS_BYMEMBERID_SUCCESS,
    GET_SKILLS_BYMEMBERID_ERROR
)<undefined, Skill[], AxiosError>();