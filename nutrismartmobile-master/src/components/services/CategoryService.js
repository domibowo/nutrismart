import axios from 'axios'

import {baseURL} from "./baseURL"

// const baseURL = "http://5f6d6480ee5a.ngrok.io/";

export async function getCategory() {
    try{
        const response = await axios.get(`${baseURL}category?page=1&size=8`)
        return response.data.content
    }catch (e) {
        console.log(e, 'service')
    }
}

export async function getProductsByCategoryId(id) {
    try {
        const products = await axios.get(`${baseURL}/category/${id}`)
        return products.data.products
    }catch (e) {
        console.log(e)
    }
}
