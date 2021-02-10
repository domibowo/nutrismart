import React, {useEffect, useState} from 'react';
import {Image, ScrollView, TouchableOpacity, View, StyleSheet, Dimensions, TextInput} from 'react-native';
import {Button, Text} from 'react-native-paper';
import Icon from 'react-native-vector-icons/dist/Ionicons';
import {commitTransaction, removeTransaction, updateTransactions} from '../../components/services/CartService';
const {width} = Dimensions.get('window');
import Mci from "react-native-vector-icons/MaterialCommunityIcons"
import Mi from "react-native-vector-icons/MaterialIcons"

export default function CobaCart (props,{navigation}) {


    const {transaction, getCart} = props
    const {transactionDetails, trxDate} = transaction

    const [quant, setQuant]=useState(1)

    const handleChangeQuantity = (i, type) =>{

    }

    useEffect(()=>{
        getCart()
    },[transactionDetails])


    const onChangeQuantity = (i, type) => {
            const transactionDetail = transactionDetails[i]
            let quantity = transactionDetail.quantity

            if (type) {
                quantity = quantity + 1
                transactionDetail.quantity = quantity
                updateTransactions(transaction)
                    .then((response) => {
                        getCart()
                    })
            } else if (!type  && quantity >= 2) {
                quantity = quantity - 1
                transactionDetail.quantity = quantity
                updateTransactions(transaction)
                    .then((response) => {
                        getCart()
                    })

            } else if (!type && quantity == 1) {
                quantity = quantity - 1
                transactionDetail.quantity = quantity
                removeTransaction(transaction, transaction.id)
                    .then((response) => {
                        getCart()
                    })
            }
        }

        const onVoucher = () =>{
            props.navigation.navigate("Voucher")
        }

        const onCheckout = (id) => {
            commitTransaction(id)
                .then((response) => {
                    props.navigation.navigate('ConfirmPayment')
                })
        }

        return (
            <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
                <View style={{flex: 1}}>
                    {
                        transactionDetails.length > 0 ?
                            <ScrollView>
                                {
                                    transactionDetails.map((transactionDetail, index) => {
                                        const {product} = transactionDetail
                                        return (
                                            <View style={styles.container}>
                                                <Image resizeMode={"contain"} style={styles.image}
                                                       source={{uri: `http://6e3075f1c843.ngrok.io/product/image/${product.id}`}}/>
                                                <View style={styles.subView}>
                                                    <View
                                                        style={{flexDirection: "row", justifyContent: "space-between"}}>
                                                        <View>
                                                            <Text style={{fontSize: 18}}>{product.name}</Text>
                                                            <Text style={{fontSize: 16}}>Rp. {product.price}</Text>
                                                        </View>
                                                    </View>
                                                    <View style={styles.product}>
                                                        <View style={{
                                                            flexDirection: 'row',
                                                            alignItems: 'center',
                                                            justifyContent: "space-between"
                                                        }}>
                                                            <View style={{flexDirection: "row"}}>
                                                                <TouchableOpacity>
                                                                    <Icon name="ios-remove-circle" size={35}
                                                                          color={"#ffa34d"}
                                                                          onPress={() => onChangeQuantity(index, false)}/>
                                                                </TouchableOpacity>
                                                                <Text style={{
                                                                    paddingHorizontal: 8,
                                                                    fontWeight: 'bold',
                                                                    fontSize: 18
                                                                }}>{transactionDetail.quantity}</Text>
                                                                <TouchableOpacity>
                                                                    <Icon name="ios-add-circle" size={35}
                                                                          color={"#ffa34d"}
                                                                          onPress={() => handleChangeQuantity(index, true)}/>
                                                                </TouchableOpacity>
                                                            </View>
                                                            <View>
                                                                <Text
                                                                    style={styles.subTotal}>Rp. {transactionDetail.subTotal}</Text>
                                                            </View>
                                                        </View>
                                                    </View>
                                                </View>
                                            </View>
                                        )
                                    })
                                }

                                <View style={{height: 20}}/>
                                <TouchableOpacity onPress={ ()=>console.log("Pressed")}>
                                <View style={{flexDirection:"row", borderBottomWidth:2,borderTopWidth:2, borderColor:"gainsboro"}}>
                                    <Mci style={{alignSelf: "center"}} name={"ticket-percent"} size={40} color={"#ffa34d"}/>
                                    <TextInput value={"Use Voucher"} style={{fontSize:18}}/>
                                    <Mi style={{alignSelf: "center", marginLeft: 190}} name={"navigate-next"} size={24} color={"black"}/>
                                </View>
                                </TouchableOpacity>
                                <View style={{justifyContent: "center"}}>
                                    <Text style={{textAlign: "right", marginRight: 10, fontWeight: "bold"}}>Grand total
                                        : {transaction.grandTotal}</Text>
                                </View>
                                <View style={{flexDirection: "row", justifyContent: "space-around"}}>
                                    <TouchableOpacity style={styles.button} onPress={() => onCheckout(transaction.id)}>
                                        <Text style={{
                                            fontSize: 17,
                                            fontWeight: "bold",
                                            color: 'white'
                                        }}>
                                            CHECKOUT
                                        </Text>
                                    </TouchableOpacity>
                                </View>
                                <View style={{height: 20}}/>
                            </ScrollView>
                            :
                            <View>
                                <Image source={require("../../../Assets/Logo/EmptyCart.png")} style={styles.image}/>
                                <Text style={{textAlign: "center", marginBottom: 20}}>Cart is Empty</Text>
                                <Button mode={"outlined"}
                                        onPress={() => navigation.navigate("Categories")}>
                                    go to shop
                                </Button>
                            </View>
                    }
                </View>
            </View>
        )
    }



const styles=StyleSheet.create({
    container : {
        width:width-20,
        margin:10,
        backgroundColor:'transparent',
        flexDirection:'row',
        borderBottomWidth:2,
        borderColor:"#cccccc",
        paddingBottom:10
    },
    image :{
        width:width/3,
        height:width/3
    },
    subView :{flex:1,
        backgroundColor:'#fff',
        padding:10, justifyContent:"space-between"
    },
    product :{
        flexDirection:'row',
        justifyContent:'space-between'
    },
    textProduct :{
        fontWeight:"bold",
        fontSize:14
    },
    quantity:{fontWeight:'bold',color:"#ffa34d",fontSize:20},
    button :{
        backgroundColor:"#ffa34d",
        width:100,
        alignItems:'center',
        padding:10,
        borderRadius:5,
        margin:20,
    },
    subTotal :{
        fontWeight:"bold",
        fontSize:18,
        alignSelf:"flex-end",
        textAlign : "center",
        marginLeft:40
    }

})
