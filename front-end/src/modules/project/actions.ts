import { createAsyncAction } from "typesafe-actions";
import { Project } from '../../api/project';
import { AxiosError } from 'axios';

export const GET_PROJECT_BYMEMBERID = 'project/GET_PROJECT_BYMEMBERID';
export const GET_PROJECT_BYMEMBERID_SUCCESS = 'project/GET_PROJECT_BYMEMBERID_SUCCESS';
export const GET_PROJECT_BYMEMBERID_ERROR = 'project/GET_PROJECT_BYMEMBERID_ERROR';

export const getProjectByMemberIdAsync = createAsyncAction(
    GET_PROJECT_BYMEMBERID,
    GET_PROJECT_BYMEMBERID_SUCCESS,
    GET_PROJECT_BYMEMBERID_ERROR
)<undefined, Project[], AxiosError>();