import React, {useEffect, useReducer} from 'react';
import {View, Text, Button, StyleSheet, StatusBar, ScrollView, Image} from 'react-native';
import { useTheme } from '@react-navigation/native';
import MyCustomCarousel from '../home/MyCustomCarousel';
import SpecialProduct from '../home/SpecialProduct';
import {CategoryReducer, initialState} from "../../components/reducers/CategoryReducer";
import {FETCH_COMPLETE, SET_LOADING} from "../../components/actions/Actions";
import {getCategory} from "../../components/services/CategoryService";
import ConntentMiddle from '../home/ConntentMiddle';

const HomeScreen = ({navigation}) => {

    const [state, dispatch] = useReducer(CategoryReducer, initialState)

    const setLoading = () => dispatch({type:SET_LOADING})
    const fetchComplete = (payload) => dispatch ({type:FETCH_COMPLETE, payload})

    const { colors } = useTheme();

    const theme = useTheme();

    const getCategories = () => {
        setLoading()
        getCategory()
            .then((categories)=>{
                fetchComplete(categories)
            })
    }

    useEffect(()=>{
        getCategories()
    },[])

    return (
        <View style={styles.container}>
            <StatusBar barStyle= { theme.dark ? "light-content" : "dark-content" }/>
            <View>
                <ScrollView>

                    <View style={styles.carousel}>
                        <MyCustomCarousel/>
                    </View>
                    <View>
                        <ConntentMiddle/>
                    </View>
                    <View style={styles.scrollView}>
                        <Text style={styles.specialProduct}>NutriSmart's Special Product</Text>
                        <ScrollView horizontal>
                            <SpecialProduct/>
                        </ScrollView>
                    </View>
                </ScrollView>
            </View>
        </View>
    );
};

export default HomeScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    header :{
        flexDirection : 'row',
        marginTop : 10,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: "gainsboro",
        margin : 14,
    },
    inputs : {
        width : '80%',
        height: 35,
        backgroundColor: "gainsboro",

    },
    image :{
        alignItems:'flex-start',
        justifyContent:'flex-start',
    },
    object :{
        justifyContent:'flex-start',
        resizeMode: 'stretch',
    },
    carousel : {
        marginTop:10,
        height : 150,
        marginLeft: 10,
        marginRight:10
    },
    category :{
        height : 200,
        marginTop :10,
    },
    background :{
        width : '100%',
        height: 200
    },
    specialProduct :{
        marginLeft: 15,
        marginBottom:10,
        fontFamily:"sans-serif",
        fontWeight:"bold"
    },
    scrollView :{
        marginTop:20,
        height: 300
    }
});
