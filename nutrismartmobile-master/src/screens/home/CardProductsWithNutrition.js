import React, {useState} from 'react';
import {Button, Card,  Paragraph, Text, Title} from 'react-native-paper';
import {Image, StyleSheet, Modal, ScrollView} from 'react-native';
import {View} from 'react-native-animatable';
import Ant from 'react-native-vector-icons/AntDesign'
import DetailProduct from '../product/DetailProduct';
import {baseURL} from '../../components/services/baseURL';
import index from 'react-native-permissions/src/index';

export default function CardProductsWithNutrition(props) {

    const {product, onClose} = props
    const {productDetail, name} = product

    const [visible, setVisible] = useState(false)

    const onOpen=()=>{
        setVisible(true)
    }

    const onCloseModal=()=>{
        setVisible(false)
    }

    return(
        <React.Fragment>
        <View style={styles.container}>
            <View style={styles.centeredView}>
                <Modal visible={visible} animationType="fade" transparent={true}
                >
                    <View style={styles.modalView}>
                        <DetailProduct productDetail={productDetail} name={name}/>
                        <Button onPress={onCloseModal} mode={"text"}>
                            <Ant name={"close"} size={24}/>
                        </Button>
                    </View>
                </Modal>
            </View>
            <View>
                <Card style={styles.card} onPress={onOpen}>
                    <Image source={{uri:`${baseURL}product/image/${product.id}`}}
                           style = {styles.image}
                           index={index}
                    />
                    <View>
                        <Text style={styles.text}>{product.name}</Text>
                    </View>
                </Card>
            </View>
        </View>
        </React.Fragment>
    )
}

const styles = StyleSheet.create({
    image :{
        height: 100,
        width:140,
        resizeMode : 'stretch',
        margin:5,
        borderColor:"#a8df65",
        borderWidth:2,
        borderRadius:14,
    },
    text :{
        fontSize:10,
        textAlign:'center'
    },
    container :{
        flexDirection:'row',
    },
    card:{
        borderRadius : 12,
        shadowOpacity:10,
    },
    btn:{
        width: 5
    },
    btnAdd:{
        width:10
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
})
