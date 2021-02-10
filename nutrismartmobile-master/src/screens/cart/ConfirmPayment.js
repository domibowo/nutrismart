import React, {useState} from 'react';
import {
    Button,
    Dimensions,
    Image,
    Modal,
    ScrollView,
    StyleSheet,
    Text,
    TextInput,
    TouchableOpacity,
    View,
} from 'react-native';

import {commitTransaction, removeTransaction} from '../../components/services/CartService';
import Mci from 'react-native-vector-icons/MaterialCommunityIcons';
import Mi from 'react-native-vector-icons/MaterialIcons';
import {baseURL} from '../../components/services/baseURL';
import AsyncStorage from '@react-native-community/async-storage';

const {width} = Dimensions.get('window');


export default function ConfirmPayment({route, navigation}) {

    const {transaction, dataCart} = route.params

    const [visible, setVisible] = useState(false)

    const {transactionDetails, grandTotal} = transaction
    const [voucher, setVoucher] = useState("")
    const [payment, setPayment] = useState(transaction.grandTotal)

    const [refreshing, setRefreshing] = React.useState(false);

    const onOpenModal = () => {
        setVisible(true)
    }

    const onCloseModal = () => {
        setVisible(false)
        navigation.navigate('Categories')
    }

    const handlePayment = (id) =>{
        commitTransaction(id)
            .then((response)=>{
                dataCart()
            })
        onOpenModal()
        AsyncStorage.removeItem('cart')
    }

    const handleCancel = () => {
        transactionDetails.map((transactionDetail)=>{
            return(
                removeTransaction(transaction, transactionDetail.id)
                    .then((transaction)=>{

                    })
            )
        })
        navigation.goBack();
    }

    function wait(timeout) {
        return new Promise(resolve => {
            setTimeout(resolve, timeout);
        });
    }

    const onRefresh = React.useCallback(() => {
        setRefreshing(true);

        wait(2000).then(() => setRefreshing(false));
    }, [refreshing]);


    return (
        <ScrollView>
            {
                transactionDetails.map((transactionDetail)=>{
                    console.log(transaction.id, 'id nya transaksi');
                    return (
                        <View style={styles.container}>
                            <Image resizeMode={"contain"} style={styles.image}
                                   source={{uri: `${baseURL}product/image/${transactionDetail.product.id}`}}/>
                                <View style={styles.subView}>
                                    <View style={{flexDirection: "column", justifyContent: "space-around", alignContent:"space-around"}}>
                                        <View>
                                        <Text style={styles.textProduct}>{transactionDetail.product.name}</Text>
                                        <Text style={styles.textProduct}>Rp. {transactionDetail.product.price}</Text>
                                        </View>
                                        <View style={{flexDirection:"row", justifyContent:"space-around", alignContent:"baseline", marginTop:20}}>
                                        <Text>Quantity : {transactionDetail.quantity}</Text>
                                        <Text>Rp.{transactionDetail.subTotal}</Text>
                                        </View>
                                    </View>
                                </View>
                        </View>
                    )
                })
            }
            <View>
            </View>
            <TouchableOpacity onPress={ ()=>navigation.navigate("Vouchers", {transactionID: transaction.id, setVoucherName:setVoucher, setPayment:setPayment})}>
                <View style={styles.sub}>
                    <Mci style={styles.ticket} name={"ticket-percent"} size={40} color={"#ffa34d"}/>
                    <Text style={{fontSize:18, alignSelf:"center"}}>NutriSmart's voucher</Text>
                    <Text style={{fontSize:18, alignSelf:"center", fontStyle:"italic", color:"#ffa34d"}}>{voucher}</Text>
                    <Mi style={styles.icon} name={"navigate-next"} size={24} color={"black"}/>
                </View>
            </TouchableOpacity>
            <View style={{flexDirection:"column", justifyContent:"space-around"}}>
                <View style={{justifyContent:"center"}}>
                    <Text style={styles.totalFooter}>Transaction total : Rp. {grandTotal}</Text>
                    <Text style={styles.totalFooter}>payment : Rp. {payment}</Text>
                </View>
                <View style={{flexDirection:"row", justifyContent:"space-around", marginTop:20}}>
                <Button color={"#ffa34d"} title={"Cancel"} onPress={()=>handleCancel()}/>
                <Button color={"#ffa34d"} title={"Confirm"} onPress={()=>handlePayment(transaction.id)}/>
                </View>
            </View>
            <Modal visible={visible} animationType="fade" transparent={false}>
                <View style={styles.modalView}>
                    <View>
                        <Image source={require("../../../Assets/Logo/giphy.gif")}
                               style={{
                                   width:100,
                                   height:100,
                                   alignSelf:"center"
                               }}/>
                               <View style={{justifyContent:"center"}}>
                                   <Text>ThankYou !!!</Text>
                                   <Text>Your order will be sent immediately</Text>
                               </View>
                    </View>
                    <Button title={"X"} onPress={onCloseModal} mode={"text"}/>

                </View>
            </Modal>
        </ScrollView>
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
        fontSize:18
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
    totalFooter : {
        fontWeight:"bold",
        alignSelf:"center",
        fontSize:20
    },
    sub :{
        flexDirection:"row",
        borderBottomWidth:2,
        borderColor:"gainsboro",
        marginBottom:10,
        justifyContent:"space-between"
    },
    ticket : {
        alignSelf: "center",
        marginLeft:5
    },
    icon :{
        alignSelf: "center",
        alignItems:"flex-end",
        alignContent:"flex-end",
        justifyContent:"flex-end",
       }


})
