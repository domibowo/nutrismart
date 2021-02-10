import {FETCH_COMPLETE, SET_LOADING} from "../actions/Actions";

const defaultValueStore={
    id : "",
    storeName:"",
    address:"",
    detail:"",
    latitude:0,
    longitude:0
}

const defaultValuesProductDetail = {
    id:"",
    description:"",
    nutritionFact:"",
    calorie:""
}

const defaultValueProduct = {
    id:"",
    name:"",
    stock:0,
    minBundle:"",
    price:0,
    photo:"",
    productDetail:{...defaultValuesProductDetail},
    details:[],
    stores:[{...defaultValueStore}]
}

const initialState = {
    categories :[{
        id:"",
        categoryName:"",
        products:[],
    }],
    isLoading : false
}

function CategoryReducer(state, action) {

    const {type, payload} = action
    switch (type) {
        case SET_LOADING :
            return {...state, isLoading: true}

        case FETCH_COMPLETE :
            return {...state, isLoading: false, categories : payload}

        default :
            return {...state}
    }

}
export {CategoryReducer, initialState}
