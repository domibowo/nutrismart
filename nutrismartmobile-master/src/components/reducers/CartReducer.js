import {ADD_TO_CART, FETCH_COMPLETE, REMOVE_FROM_CART, SET_LOADING} from '../actions/Actions';

const defaultTransaction = {
    id:"",
    subTotal:"",
    quantity:"",
    price:0,
    product:{}
}

const defaultCart = {
    id:"",
    grandTotal:0,
    voucher:{},
    transactionDetails:[{...defaultTransaction}],
    trxDate:"",
    statusEnum:"",
    account:{},
    productId: null,
    productQty: null,
    accountId: null,
    voucherName: null,
    detailId:null
}

const defaultValue ={
    productId : "",
    productQty : 0,
}

const initialState = {
    cartItem : [{...defaultCart}],
    isLoading : false,
    productsCart : [{...defaultValue}]
}

function CartReducer(state, action) {
    const {type, payload} = action

    switch (type) {
        case ADD_TO_CART:
            console.log(payload,'direducer');
            return{...state, cartItem: [...state.cartItem, payload]}

        case REMOVE_FROM_CART:
            return {...state, cartItem: state.cartItem.filter(carItem => carItem.id !== payload.id)}

        case SET_LOADING :
            return {...state, isLoading : true}

        case FETCH_COMPLETE :
            return {...state, isLoading: false, cartItem: payload}

        default :
            return {...state}
    }
}

export {CartReducer, initialState}

