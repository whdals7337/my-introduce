import { getSkillsByMemberIdAsync } from './actions';
import { getSkillsByMemberId } from '../../api/skill'
import createAsyncThunk from '../../lib/createAsyncThunk';

export const getSkillsByMemberIdThunk = createAsyncThunk(getSkillsByMemberIdAsync, getSkillsByMemberId);