import { combineReducers} from 'redux';
import member from './member'
import project from './project'
import skill from './skill'

const rootReducer = combineReducers({
    member,
    project,
    skill
});


export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;