import React, {useState} from "react";
import {Image, StyleSheet, TouchableOpacity, View} from 'react-native';
import {TextInput, Text, Button} from "react-native-paper";
import Mate from 'react-native-vector-icons/dist/MaterialIcons'
import Antd from "react-native-vector-icons/AntDesign"

export default function Calculator(props) {

    const [height, setHeight]=useState(0)
    const [mass, setMass]=useState(0)
    const [resultNumber, setResultNumber]=useState(0)
    const [resultText, setResultText]= useState("")
    const [image, setImage]=useState("")

    const {onClose}=props

    const handleCalculate = () =>{
        let imc = (mass ) / (height/100) ** 2;
        setResultNumber(imc.toFixed(2))

        if(imc < 18.5){
            setResultText("UnderWeight")

        } else if (imc > 18.5 && imc < 25) {
            setResultText("Normal Weight")
        } else if (imc >= 25 && imc < 30) {
            setResultText("Overweight")
        } else if (imc >= 30 && imc < 100){
            setResultText("Obesity")
        } else {
            setResultText("NULL")
        }
    }

    return(
        <View >
            <View style={{flexDirection:"row", weight:100, height:50, backgroundColor:"#a8df65"}}>
                <TouchableOpacity onPress={onClose} style={{flexDirection:"row"}}>
                    <Antd name={"arrowleft"} size={24} color={"black"} style={{ alignSelf:"center", marginLeft:10}}/>
                    <Text style={{marginTop: 5, marginLeft: 90, fontSize:20, textAlign: "center", color:"black", justifyContent:"center", alignSelf:"center"}}>BMI Calculator</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.container}>
               <Image source={require("../../../Assets/Logo/bmi.jpeg")} style={{width:120, height:120}}/>
                <View style={styles.intro} >
                    <TextInput
                        label={"Height"}
                        style={styles.input}
                        onChangeText={(val)=>setHeight(val)}
                        keyboardType={"numeric"}
                    />
                    <TextInput
                        label={"Weight"}
                        style={styles.input}
                        keyboardType={"numeric"}
                        onChangeText={(val)=>setMass(val)}
                    />
                </View>
                <View style={{marginTop: 20}}>
                    <Button
                        style={styles.button}
                        onPress={handleCalculate}>
                        <Text style={styles.buttonText}>Calculate</Text>
                    </Button>
                </View>

                <Text style={styles.result}>
                    {resultNumber}
                    <Text style={{fontSize: 20, fontWeight: '300'}}>
                        Kg/m²
                    </Text>
                </Text>
                <View style={styles.divider}/>
                <Text style={[styles.result, { fontSize: 35 }]}>{resultText}</Text>
            </View>

        </View>
    )
}
const styles = StyleSheet.create({
    container: {
        alignItems: 'center'
    },
    intro: {
        flexDirection: "row",
        marginRight:5,
        marginLeft:5
    },
    input: {
        height: 70,
        textAlign: "center",
        width: "45%",
        fontSize: 20,
        marginTop: 24,
        backgroundColor: '#d8ebb5',
        marginLeft: 5,
        marginRight: 5
    },
    button: {
        backgroundColor: "#a8df65",
        width: 200,
        height:50,
        justifyContent:"center",
        alignContent:"center",
        // marginLeft:80
    },
    buttonText: {
        alignSelf: "center",
        padding: 20,
        fontSize: 25,
        color: "white",
        fontWeight: "bold"
    },
    result: {
        marginTop: 30,
        alignSelf: "center",
        color: "black",
        fontSize: 65,
        // padding: 15
    },
    title: {
        color: "black",
        justifyContent: "center",
        alignSelf: "center",
        marginTop: 30,
        fontSize: 25
    },
    divider: {
        width: 300,
        height: 1,
        backgroundColor: '#bababa',
        margin: 8
    }
});


