import React, {useEffect, useReducer} from 'react';
import {Button, Card,  Paragraph, Text, Title} from 'react-native-paper';
import {Image, ScrollView, StyleSheet} from 'react-native';
import {View} from 'react-native-animatable';
import AsyncStorage from '@react-native-community/async-storage';
import {baseURL} from '../../components/services/baseURL';

function CardProduct(props) {

    const {product} = props

    const handleProductToCart = (product) =>{
        const itemCart = {
            products : product,
            productQty : 1,
            name : product.name,
            price : product.price,
            id : product.id
        }
        AsyncStorage.getItem('cart').then((dataCart)=>{
            if(dataCart !== null){
                const cart = JSON.parse(dataCart)
                cart.push(itemCart)
                AsyncStorage.setItem('cart', JSON.stringify(cart))
            }
            else {
                const cart = []
                cart.push(itemCart)
                AsyncStorage.setItem('cart', JSON.stringify(cart))
            }
            alert("Added to cart")
        })
            .catch((err)=>{
                alert(err)
            })
    }

    return(
    <React.Fragment>
        <ScrollView horizontal={true} style={styles.container}>
                <Image source={{uri:`${baseURL}product/image/${product.id}`}}
                            style = {styles.image}
                />
                <View style={styles.componentBox}>
                    <Text style={styles.text}>{product.name}</Text>
                    <Text style={styles.price}>Rp. {product.price}</Text>
                    <Text>stock : {product.stock}</Text>
                    <Button mode={"contained"}
                            color={"#ffa34d"}
                            onPress={()=>handleProductToCart(product)}
                    >
                        Add to cart
                    </Button>
                </View>
        </ScrollView>
        <View style={styles.divider}/>
    </React.Fragment>
    )
}



export default CardProduct
const styles = StyleSheet.create({
    image :{
        height: 120,
        width:140,
        margin:5,
        borderColor:"#bababa",
        borderWidth:1,

    },
    componentBox: {
        marginLeft: 25,
        marginRight: 5,
        alignItems:'flex-start'
    },
    text :{
        fontSize:19,
        fontWeight: 'bold'
    },
    price :{
        fontSize:16,
        textAlign:'center',
        margin:10,

    },
    container :{
        flex: 1,
        flexDirection:'row',
    },
    card:{
        borderRadius : 12,
        shadowOpacity:10,
    },
    btn:{
        width: 5
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
    divider: {
        width: 500,
        height: 1,
        backgroundColor: '#bababa',
        margin: 8
    }
})

