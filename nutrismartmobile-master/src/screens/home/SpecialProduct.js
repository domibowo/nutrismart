import React from "react";
import {Image, ScrollView, View, StyleSheet} from "react-native";
import {Card, Text, Title} from "react-native-paper";

export default function SpecialProduct() {
    return(
        <View>
            <View style={styles.container}>
                <View style={styles.scrollView}>
                    <Card style={styles.card}>
                        <Card.Cover source={require("../../../Assets/special/carrot.jpeg")} style={styles.product}/>
                        <Title style={styles.text}>Vegetable</Title>
                    </Card>
                </View>
                <View style={styles.scrollView}>
                    <Card style={styles.card}>
                        <Image source={require("../../../Assets/special/meat.jpeg")} style={styles.product}/>
                        <Text style={styles.text}>Meat</Text>
                    </Card>
                </View>
                <View style={styles.scrollView}>
                    <Card style={styles.card}>
                        <Image source={require("../../../Assets/special/lidahbuaya.jpeg")} style={styles.product}/>
                        <Text style={styles.text}>Meat</Text>
                    </Card>
                </View>
                <View style={styles.scrollView}>
                    <Card style={styles.card}>
                        <Image source={require("../../../Assets/special/kiwi.jpeg")} style={styles.product}/>
                        <Text style={styles.text}>Meat</Text>
                    </Card>
                </View>
            </View>
        </View>

    )
}
const styles = StyleSheet.create({
    product :{
        width:100,
        height:150
    },
    container :{
        flexDirection:'row'
    },
    scrollView :{
        marginRight : 1,
        marginLeft : 15,
        height: 100
    },
    card:{
        borderRadius : 12,
        shadowOpacity:10,
    },
    text :{
        fontSize:10,
        textAlign:'center'
    },

})
