import { getMemberAsync } from './actions';
import { getMemberById } from '../../api/member'
import createAsyncThunk from '../../lib/createAsyncThunk';
//import {Dispatch} from 'redux';

// export function getMemberThunk(id: number) {
//     return async (dispatch: Dispatch) => {
//         const {request, success, failure} = getMemberAsync;
//         dispatch(request());
//         try {
//             const member = await getMemberById(id);
//             dispatch(success(member));
//         }catch (e) {
//             dispatch(failure(e));
//         }
//     };
// }

export const getMemberThunk = createAsyncThunk(getMemberAsync, getMemberById);