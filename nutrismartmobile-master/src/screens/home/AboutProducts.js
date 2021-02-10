import React, {useReducer} from "react";
import {ScrollView, View} from "react-native";
import {Button, Card, Text, Title} from "react-native-paper";
import MaterialIcons from "react-native-vector-icons/dist/MaterialIcons";
import CardProductsWithNutrition from "./CardProductsWithNutrition";
import AntD from "react-native-vector-icons/AntDesign"

export default function AboutProducts(props) {

    const {products, onClose} = props

    return(
        <Card>
            <View style={{flexDirection:'row', backgroundColor:"#fbffc9", height : 50}}>
                <Button onPress={onClose} mode={"text"}>
                    <AntD
                        name="arrowleft"
                        color="black"
                        size={24}
                        style={{alignSelf:"center"}}
                    />
                </Button>
                <Title style={{color:"black", alignSelf:"center" ,justifyContent:"center", alignItems:"center", alignContent:"center"}}>Product</Title>
            </View>
            <Card.Content>
                <ScrollView>
                    <View style={{flexDirection:"row", flexWrap: 'wrap'}}>

                    {
                        products.map((product, index)=>{
                            return(
                                <CardProductsWithNutrition
                                    key={index}
                                    product={product}
                                />
                            )
                        })
                    }
                    </View>

                </ScrollView>
            </Card.Content>
        </Card>
    )
}
