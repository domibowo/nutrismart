import React from 'react';
import {View} from 'react-native';
import {Text} from 'react-native-paper';

function DetailProduct(props) {
    const {productDetail, name} = props
    return(
        <View>
            <Text style={{fontSize:20, fontWeight:"bold", textAlign:"center", marginBottom:10}}>{name}</Text>
            <Text>Calorie : {productDetail.calorie}</Text>
            <Text>weight : {productDetail.description}</Text>
            <Text>nutrition : {productDetail.nutritionFact}</Text>
        </View>
    )
}
export default DetailProduct
