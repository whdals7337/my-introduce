import { getMemberAsync } from './actions';
import { getSelectedMember } from '../../api/member'
import createAsyncThunk from '../../lib/createAsyncThunk';

export const getMemberThunk = createAsyncThunk(getMemberAsync, getSelectedMember);