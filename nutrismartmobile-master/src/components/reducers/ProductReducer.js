import {FETCH_COMPLETE, SET_LOADING} from '../actions/Actions';

const initialState = {
    isLoading:false,
    products:[]
}

function ProductReducer(state, action) {
    const {type, payload} = action

    switch (type) {
        case FETCH_COMPLETE:
            return{...state,isLoading: false, products: [...payload]}

        case SET_LOADING:
            return {...state, isLoading:true}

        default :
            return {...state}
    }
}

export {ProductReducer, initialState}
