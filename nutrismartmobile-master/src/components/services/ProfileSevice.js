import axios from 'axios'
import {baseURL} from "./baseURL"

export async function getProfile(id) {
    try{
        console.log(id, 'id');
        const response = await axios.get(`${baseURL}account/profile/${id}`)
        return response.data.profile
    }catch (e) {
        console.log(e, 'service get profile')
    }
}

export async function postProfile(form) {
    try {
        console.log(form, 'profile diterima service');

        const response = await axios.put(`${baseURL}account/profile`, form)
        console.log(response.data, 'respoonse dari backend');
        return response.data
    }catch (e) {
        console.log(e,'service');
    }
}

export async function putProfile(id) {
    try {
        const response = await axios.put(`${baseURL}admin/accounts/${id}`)
        return response.data
    }catch (e) {
        console.log(e, 'service');
    }
}
