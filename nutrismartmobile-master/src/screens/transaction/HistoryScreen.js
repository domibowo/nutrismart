import React, {useEffect, useReducer} from 'react';
import {View, Text, Button, StyleSheet, ScrollView, Image} from 'react-native';
import AsyncStorage from '@react-native-community/async-storage';
import { getHistory} from '../../components/services/CartService';
import {CartReducer, initialState} from '../../components/reducers/CartReducer';
import {FETCH_COMPLETE, SET_LOADING} from '../../components/actions/Actions';
import CardHistory from './CardHistory';

const HistoryScreen = (props) => {

    const [state, dispatch] = useReducer(CartReducer, initialState)

    const {cartItem} = state

    const setLoading = () => dispatch({type:SET_LOADING})
    const fetchComplete = (payload) => dispatch({type:FETCH_COMPLETE, payload})

    const getId = async () =>{
        try {
            const id = await AsyncStorage.getItem('userToken')
            setLoading()
            getHistory(id)
                .then((cart)=>{
                    fetchComplete(cart)
                })

        }catch (e) {
            console.log(e, 'error di profile');
        }
    }

    useEffect(()=>{
        getId()
    },[])


    return (
        <View style={{flex:1,alignItems: 'center', justifyContent: 'center'}}>
            <View style={{flex:1}}>
            {
                cartItem.length > 0 ?
                    <ScrollView>
                        {
                            cartItem.map((transaction, index) => {
                                return (
                                    <CardHistory
                                        key={index}
                                        transaction={transaction}
                                    />
                                )
                            })
                        }
                    </ScrollView> :
                    <View style={styles.view}>
                        <Image style={styles.image} source={require("../../../Assets/Other/emptyh.png")}/>
                        <View style={styles.button}>
                        <Button style={styles.button} title={"Let's shopping"} onPress={()=>props.navigation.navigate("Home")}/>
                        </View>
                    </View>
            }
            </View>
        </View>
    );
};

export default HistoryScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    image : {
        width:150,
        height:150,
        alignSelf:"center",
        marginTop:100
    },
    view:{
        justifyContent: "center",
        alignContent: "center"
    },
    button : {
        justifyContent:"center",
        width: 100,
        marginLeft:50,
        marginRight:50
    }

});
