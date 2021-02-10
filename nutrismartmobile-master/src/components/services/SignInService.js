import axios from 'axios'
import {baseURL} from "./baseURL"

// const baseURL = "http://5f6d6480ee5a.ngrok.io/";

export async function signInAccount(form) {
    console.log(`FORM : `, form);
    try{
        const response = await axios.post(`${baseURL}sign-in`, form)
        console.log(response.data, 'response');
        return response.data
    }catch (e) {
        console.log(e, 'service')
    }
}

export async function signUpAccount(form) {
    try{
        const response = await axios.post(`${baseURL}sign-up`, form)
        if(response.status == 200) return true
        else return false
    }catch (e) {

    }
}
