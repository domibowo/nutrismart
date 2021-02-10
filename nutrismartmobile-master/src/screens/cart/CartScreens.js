import React from 'react';
import AsyncStorage from '@react-native-community/async-storage';
import {Button, Text} from 'react-native-paper';
import {Dimensions, Image, ScrollView, TouchableOpacity, View, StyleSheet, CheckBox} from 'react-native';
import Icon from 'react-native-vector-icons/dist/Ionicons';
import {getCart, postProductToCart} from '../../components/services/CartService';
const {width} = Dimensions.get('window');
import {baseURL} from '../../components/services/baseURL';

export default class CartScreens extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            dataCart : [],
        }
    }

    handleDataCart = () => {
        this.setState({dataCart : []})
    }

    handleCheckout = async (dataCart) => {

        const accountId =  await AsyncStorage.getItem('userToken')
        {
            dataCart.map((product)=>{
                postProductToCart(accountId, product.id, product.productQty)
                    .then((transaction) =>{
                       this.props.navigation.navigate('ConfirmPayment', {transaction:transaction, dataCart:this.handleDataCart})
                    })
            })
        }
    }

    onChangeQuantity(i,type) {
        const newDataCart = this.state.dataCart
        let quantity = newDataCart[i].productQty;

        if (type) {
            quantity = quantity + 1
            newDataCart[i].productQty = quantity
            this.setState({dataCart:newDataCart})
            AsyncStorage.setItem('cart', JSON.stringify(newDataCart))
        }
        else if (type==false && quantity>=2){
            quantity = quantity - 1
            newDataCart[i].productQty = quantity
            this.setState({dataCart:newDataCart})
            AsyncStorage.setItem('cart', JSON.stringify(newDataCart))
        }
        else if (type==false && quantity==1){
            newDataCart.splice(i,1)
            this.setState({dataCart:newDataCart})
            AsyncStorage.setItem('cart', JSON.stringify(newDataCart))
        }
    }

    componentDidMount() {
        AsyncStorage.getItem('cart').then((cart)=>{
            if(cart != null){
                const product = JSON.parse(cart)
                this.setState({dataCart : product})
            }
        })
            .catch((err)=>{
                alert(err)
            })
    }
    render() {
        return (
            <View style={{flex:1,alignItems: 'center', justifyContent: 'center'}}>
                <View style={{flex:1}}>
                    {
                        this.state.dataCart.length > 0 ?
                            <ScrollView>
                                {
                                    this.state.dataCart.map((product, index) => {
                                        const {products} = product
                                        return (
                                            <View style={styles.container}>
                                                <Image resizeMode={"contain"} style={styles.image}
                                                       source={{uri: `${baseURL}product/image/${product.id}`}}/>
                                                <View style={styles.subView}>
                                                    <View
                                                        style={{flexDirection: "row", justifyContent: "space-between"}}>
                                                        <View>
                                                            <Text style={styles.textProduct}>{product.name}</Text>
                                                            <Text style={styles.textProduct}>Rp. {product.price}</Text>
                                                        </View>
                                                    </View>
                                                    <View style={styles.product}>
                                                        <Text
                                                            style={styles.quantity}>Rp. {product.price * product.productQty}</Text>
                                                        <View style={{flexDirection: 'row', alignItems: 'center'}}>
                                                            <TouchableOpacity>
                                                                <Icon name="ios-remove-circle" size={35}
                                                                      color={"#ffa34d"}
                                                                      onPress={() => this.onChangeQuantity(index, false)}/>
                                                            </TouchableOpacity>
                                                            <Text style={{
                                                                paddingHorizontal: 8,
                                                                fontWeight: 'bold',
                                                                fontSize: 18
                                                            }}>{product.productQty}</Text>
                                                            <TouchableOpacity>
                                                                <Icon name="ios-add-circle" size={35} color={"#ffa34d"}
                                                                      onPress={() => this.onChangeQuantity(index, true)}/>
                                                            </TouchableOpacity>
                                                        </View>
                                                    </View>
                                                </View>
                                            </View>
                                        )
                                    })
                                }

                                <View style={{height: 20}}/>
                                <View style={{flexDirection: "row", justifyContent: "space-around"}}>
                                    <TouchableOpacity style={styles.button} onPress={()=>this.handleCheckout(this.state.dataCart)}>
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
                                <Text style={{textAlign:"center", marginBottom:20}}>Cart is Empty</Text>
                                <Button mode={"outlined"}
                                        onPress={()=>this.props.navigation.navigate("Categories")}>
                                    go to shop
                                </Button>
                            </View>
                    }
                </View>

            </View>

        );
    }
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
    }

})
