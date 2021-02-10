import {FETCH_COMPLETE, HANDLE_INPUT_CHANGES, SET_LOADING} from '../actions/Actions';

const defaultValue = {
    address : "",
    birthDate :"",
    detail : "",
    firstName : "",
    gender : "",
    lastName:"",
    latitude:"",
    longitude:"",
    phone:"",
    status:""
}

const initialState = {
    profile : {...defaultValue},
    isLoading:false
}

function ProfileReducer(state, action) {
    const {type, payload} = action

    switch (type) {
        case SET_LOADING :
            return{...state, isLoading: true}

        case FETCH_COMPLETE :
            console.log(payload,'payload profile');
            return {...state, isLoading: false, profile: {...payload}}

        case HANDLE_INPUT_CHANGES :
            const profile = {...state.profile}
            const {inputName, inputValue} = payload
            profile[inputName] = inputValue
            return {...state, profile: profile}

        default :
            return {...state}
    }
}

export {ProfileReducer, initialState}
