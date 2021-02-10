import React, {useEffect, useReducer, useState} from 'react';
import {Image, View, StyleSheet, Modal, ScrollView} from "react-native";
import {Button, Card, Text, Title} from "react-native-paper";
import {getProductsByCategoryId} from "../../components/services/CategoryService";
import {initialState, ProductReducer} from "../../components/reducers/ProductReducer";
import {FETCH_COMPLETE, SET_LOADING} from "../../components/actions/Actions";
import ProductForSearch from '../tabscreens/ProductForSearch';

export default function Categories(props) {

    const [state, dispatch] = useReducer(ProductReducer, initialState)

    const fetchComplete = (payload) => dispatch({type:FETCH_COMPLETE, payload})
    const setLoading = () => dispatch({type:SET_LOADING})

    const handlePress = (id) =>{
        setLoading()
        getProductsByCategoryId(id)
            .then((products)=>{
                fetchComplete(products)
                props.navigation.navigate('Product', {products:products})
            })
    }


    return(
        <View>
            <View>
            <View style={styles.view}>
                <Card style={styles.card}
                       onPress={() => handlePress("ff808181725ac19101725ac69f1b0007")}
                >
                    <Card.Cover source={require("../../../Assets/Fruits/fruits.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Fruits</Title>
                </Card>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac62de00004")}>
                    <Card.Cover source={require("../../../Assets/Roots/Roots.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Roots</Title>
                </Card>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac605110003")}>
                    <Card.Cover source={require("../../../Assets/ProcessedFood/Processed.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Processed</Title>
                </Card>
            </View>
            <View style={styles.view}>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac64cbe0005")}>
                    <Card.Cover source={require("../../../Assets/Seeds/seeds.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Seeds</Title>
                </Card>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac5ba310002")}>
                    <Card.Cover source={require("../../../Assets/Other/Other.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Others</Title>
                </Card>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac6cd180008")}>
                    <Card.Cover source={require("../../../Assets/Vegie/vegie-1.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Veggies</Title>
                </Card>
            </View>
            <View style={styles.view}>
                <Card style={styles.card} onPress={()=>handlePress("ff808181725ac19101725ac67ae60006")}>
                    <Card.Cover source={require("../../../Assets/Meat/1.png")}
                                style={styles.image}/>
                    <Title style={styles.title}>Animal's </Title>
                </Card>
            </View>
            </View>
        </View>

    )
}

const styles = StyleSheet.create({
    container :{
        margin : 15,
        flex : 1
    },
    view :{
        flexDirection:'row',
        justifyContent:'space-around',
    },
    subview :{

    },
    image :{
        resizeMode:'stretch',
        width:75,
        height:65,
        marginTop: 20,
        marginBottom:20,
        borderWidth:2,
        borderRadius:14,
        opacity:12,
        borderColor:"white"
    },
    card:{
        borderRadius : 12,
        borderWidth: 2,
        borderColor: "#ddd",
        shadowColor:"#000",
        shadowOpacity:1.0,
        shadowRadius:2,
        elevation:1,
        marginTop:15,
        marginRight:5,
        marginLeft:5,
        shadowOffset:{width: 0, height: 2},
    },
    title:{
        textAlign:"center",
        fontSize:12,
        marginTop: -20
    }
})
