import {LOGIN, LOGOUT, REGISTER, RETRIEVE_TOKEN} from '../actions/Actions';

const initialLoginState = {
    isLoading: true,
    userName: null,
    userToken: null,
};

function LoginReducer  (prevState, action)  {
    switch( action.type ) {
        case RETRIEVE_TOKEN:
            return {...prevState, userToken: action.token, isLoading: false,
            };
        case LOGIN:
            return {...prevState, userName: action.userName, userToken: action.token, isLoading: false,
            };
        case LOGOUT:
            return {...prevState, userName: null, userToken: null, isLoading: false,
            };
        case REGISTER:
            return {...prevState, userName: action.id, userToken: action.token, isLoading: false,
            };
        default :
            return {...prevState}
    }
};
export {LoginReducer, initialLoginState}
