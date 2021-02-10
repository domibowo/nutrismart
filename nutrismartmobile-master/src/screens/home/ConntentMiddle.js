import React, {useEffect, useReducer, useState} from "react";
import {Modal, StyleSheet, View} from "react-native";
import {Card, Title} from "react-native-paper";
import {initialState, ProductReducer} from "../../components/reducers/ProductReducer";
import {FETCH_COMPLETE} from "../../components/actions/Actions";
import {getProducts} from "../../components/services/ProductService";
import AboutProducts from "./AboutProducts";
import Calculator from "./Calculator";

export default function ContentMiddle() {

    const [state, dispatch] = useReducer(ProductReducer, initialState)

    const {products} = state

    const fetchComplete = (payload) => dispatch({type:FETCH_COMPLETE, payload})

    const [visible, setVisible] = useState(false)
    const [visibleBmi, setVisibleBmi] = useState(false)

    const onOpenBmi =()=>{
        setVisibleBmi(true)
    }

    const onCloseBmi = () =>{
        setVisibleBmi(false)
    }

    const onOpen = () =>{
        setVisible(true)
    }

    const onClose = () =>{
        setVisible(false)
    }


    const getAllProducts = () =>{
        getProducts()
            .then((products)=>{
                fetchComplete(products)
            })
    }

    useEffect(()=>{
        getAllProducts()
    },[])

    return(
        <View>
            <View style={styles.view}>
            <Card style={styles.card}
                   onPress={() =>onOpen()}
            >
                <Card.Cover source={require("../../../Assets/contentMidle/1.png")}
                            style={styles.image}/>
            </Card>
            <Card style={styles.card}
                  onPress={()=> onOpenBmi()}
            >
                <Card.Cover source={require("../../../Assets/contentMidle/2.png")}
                            style={styles.image}/>
            </Card>
            </View>
            <View style={styles.view}>
                <Card style={styles.card}>
                    <Card.Cover source={require("../../../Assets/contentMidle/3.png")}
                                style={styles.image}/>
                </Card>
                <Card style={styles.card}
                >
                    <Card.Cover source={require("../../../Assets/contentMidle/4.png")}
                                style={styles.image}/>
                </Card>
            </View>
            <Modal visible={visible} mode={"fade"}>
                <View style={styles.modalView}>
                    <AboutProducts products={products} onClose={onClose}/>
                </View>
            </Modal>
            <Modal visible={visibleBmi} mode={"fade"}>
                <View style={styles.modalView}>
                    <Calculator onClose={onCloseBmi}/>
                </View>
            </Modal>
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
        width:120,
        height:120,
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
        marginTop:5,
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
