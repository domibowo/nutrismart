import axios from 'axios'

import {baseURL} from "./baseURL"


export async function getProducts() {
    try{
        const response = await axios.get(`${baseURL}product?page=1&size=50`)
        return response.data.content
    }catch (e) {
        console.log(e, 'service')
    }
}
