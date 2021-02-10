import {FETCH_COMPLETE, SET_LOADING} from '../actions/Actions';

const initialState = {
    isLoading:false,
    vouchers : []
}

function VoucherReducer(state, action) {
    const {type, payload} = action

    switch (type) {
        case SET_LOADING :
            return{...state, isLoading : true}

        case FETCH_COMPLETE :
            return {...state, isLoading: false, vouchers : payload}

        default:
            return {...state}
    }
}

export {VoucherReducer, initialState}
