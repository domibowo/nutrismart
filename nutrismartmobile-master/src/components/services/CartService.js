import axios from 'axios'
import {baseURL} from "./baseURL"

export async function getCart(id) {
    try{
        const response = await axios.get(`${baseURL}account/transaction/cart/${id}?page=1&size=10`)
        return response.data.content
    }catch (e) {
        console.log(e, 'service dicart')
    }
}
export async function getHistory(id) {
    try{
        const response = await axios.get(`${baseURL}account/transaction/history/${id}?page=1&size=10`)
        return response.data.content
    }catch (e) {
        console.log(e, 'service dicart')
    }
}
export async function postProductToCart(accountId, productId, productQty) {
    try{
        const form = {productId:productId, accountId:accountId, productQty:productQty}
        const response = await axios.post(`${baseURL}account/transaction/cart`, form)
        return response.data
    }catch (e) {
        console.log(e, 'service post product to cart')
    }
}

export async function commitTransaction(transactionId) {
    try{
        const response = await axios.put(`${baseURL}account/transaction/purchased/${transactionId}`)
        return response.data
    }catch (e) {
        console.log(e, 'service dicart')
    }
}

export async function updateTransactions(transaction) {
    try {
        const response = await axios.put(`${baseURL}account/transaction/update`, transaction)
        return response.data
    }catch (e) {
        console.log(e, 'error update transaction');
    }
}

export async function removeTransaction(transaction, id) {
    try{
        const response = await axios.put(`${baseURL}account/transaction/cart/${id}`, transaction)
        return response.data
    } catch (e) {
        console.log(e);
    }
}

export async function getVouchers() {
    try {
        const vouchers = await axios.get(`${baseURL}voucher?page=1&size=10`)
        return vouchers.data.content
    }catch (e) {
        console.log(e);
    }
}

export async function setVoucher(name, id) {
    try {

        const form = {voucherName : name}
        const transaction = await axios.put(`${baseURL}account/transaction/cart/voucher/${id}`, form)
        console.log(transaction.data,'resonse ketika update transaksi set voucher');
        return transaction.data
    }catch (e) {

    }
}
