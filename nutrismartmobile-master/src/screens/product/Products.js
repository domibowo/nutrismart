import React, {useState} from "react";
import {Modal, ScrollView, TouchableOpacity, View} from "react-native";
import {Button, Card, Text, Title} from "react-native-paper";
import CardProduct from "./CardProduct";

export default function Products({route, navigation}) {

    const {products} = route.params

    return(
        <Card>
        <Card.Content>
        <ScrollView>
            {
                products.map((product, index)=>{
                    return(
                        <CardProduct
                        key={index}
                        product={product}
                        />
                    )
                })
            }
        </ScrollView>
        </Card.Content>
        </Card>
    )
}
