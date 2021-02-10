import React, {useEffect, useReducer} from 'react';
import {ScrollView, Text, TouchableOpacity, View, StyleSheet, Modal} from 'react-native';
import {getVouchers, setVoucher} from '../../components/services/CartService';
import {initialState, VoucherReducer} from '../../components/reducers/VoucherReducer';
import {FETCH_COMPLETE, SET_LOADING} from '../../components/actions/Actions';
import Feather from "react-native-vector-icons/Feather"
import NumberFormat from 'react-number-format';

export default function VoucherScreen ({route, navigation}){

    const {transactionID, setVoucherName, setPayment} = route.params

    const [state, dispatch] = useReducer(VoucherReducer, initialState)

    const {isLoading, vouchers} =state


    const setLoading = () => dispatch({type:SET_LOADING})
    const fetchComplete = (payload) => dispatch({type:FETCH_COMPLETE, payload})

    const getAllVoucher = () => {
        setLoading()
        getVouchers()
            .then((vouchers)=>{
                fetchComplete(vouchers)
            })
    }

    const insertVoucher =(name) =>{
        setVoucher(name, transactionID)
            .then((transaction)=>{
                setVoucherName(transaction.voucher.name)
                setPayment(transaction.grandTotal)
                navigation.navigate("ConfirmPayment")
            })
    }

    useEffect(()=>{
        getAllVoucher()
    },[])

        return (
            <View>

                {
                    vouchers.map((voucher, index)=>{
                        const {name} = voucher
                        return(
                            <ScrollView >
                                <TouchableOpacity onPress={()=>insertVoucher(name)}>

                                <View style={styles.container}>
                                <Feather name={"scissors"} size={40} color={"#ffa34d"}/>
                                <View>
                                    <Text style={{fontWeight:"bold", fontSize:18}}>Voucher code {voucher.name}</Text>
                                    <Text style={{fontSize:12,}}>Valid : {voucher.valid}</Text>
                                </View>
                                    <View style={styles.discount}>
                                        <NumberFormat value={voucher.value} displayType={'text'} decimalScale={2} renderText={discount => <Text>{discount}</Text>}/>
                                    </View>
                                </View>
                                </TouchableOpacity>
                            </ScrollView>
                        )
                    })
                }
            </View>

        )
}
const styles = StyleSheet.create({
    container :{
        borderBottomWidth:2,
        flexDirection:"row",
        borderBottomColor:"gainsboro",
        marginRight:5,
        marginLeft:5,
        marginTop:10,
        justifyContent: "space-between"
    },
    discount : {borderWidth:2,
        borderColor:"#ffa34d",
        width:50,
        justifyContent:"center",
        alignContent:"center",
        alignItems:"center",

    },
    centeredView: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        marginTop: 22,
        height:50,
        width:50
    },
    modalView: {
        margin: 20,
        backgroundColor: "white",
        borderRadius: 20,
        padding: 35,
        alignItems: "center",
        shadowColor: "#000",
        shadowOffset: {
            width: 0,
            height: 2
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5
    },

})
