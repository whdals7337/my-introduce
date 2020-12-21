import { getProjectByMemberIdAsync } from './actions';
import { getProjectsByMemberId } from '../../api/project'
import createAsyncThunk from '../../lib/createAsyncThunk';

export const getProjectsByMemberIdThunk = createAsyncThunk(getProjectByMemberIdAsync, getProjectsByMemberId);