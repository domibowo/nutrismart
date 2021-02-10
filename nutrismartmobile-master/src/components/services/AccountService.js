import axios from 'axios'
import {baseURL} from "./baseURL"

export async function getAccount(id) {
    try {
        const account = await axios.get(`${baseURL}account/profile/${id}`)
        return account.data
    } catch (e) {
        console.log(e);
    }
}
