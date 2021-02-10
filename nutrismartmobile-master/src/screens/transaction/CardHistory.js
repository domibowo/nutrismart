import React from 'react';
import { Paragraph, Text, Title} from 'react-native-paper';
import {Dimensions, Image, ScrollView, StyleSheet} from 'react-native';
import {View} from 'react-native-animatable';
import {baseURL} from '../../components/services/baseURL';
const {width} = Dimensions.get('window');


function CardHistory(props) {

    const {transaction} = props
    console.log(transaction);
    const {transactionDetails, trxDate} = transaction

    return(

        <View style={styles.container}>
            <View>
                    {
                        transactionDetails.map((transactionDetail, index)=>{
                            return(
                                <ScrollView style={styles.container}>
                                            <Image source={{uri:`${baseURL}product/image/${transactionDetail.product.id}`}}
                                                        style = {styles.image}
                                            />
                                            <Text style={{color:"black"}}>{trxDate}</Text>
                                    <View style={styles.subView}>
                                            <Text style={styles.text}>{transactionDetail.product.name}</Text>
                                            <Text style={styles.textPrice}>Rp. {transactionDetail.price}</Text>
                                        <View style={{marginTop:20}}>
                                            <Text style={{textAlign: "right"}}> Rp. {transactionDetail.subTotal}</Text>
                                        </View>
                                    </View>
                                </ScrollView>
                            )
                        })
                    }
            </View>
        </View>
    )
}



export default CardHistory
const styles = StyleSheet.create({
    image :{
        width:width/3,
        height:width/3
    },
    text :{
        fontSize:17,
        textAlign:'left'
    },
    textPrice :{
        fontSize:12,
        textAlign:'left'
    },
    container : {
        width:width-20,
        margin:10,
        backgroundColor:'transparent',
        flexDirection:'row',
        borderBottomWidth:2,
        borderColor:"#cccccc",
        paddingBottom:10
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
    subView :{flex:1,
        backgroundColor:'#fff',
        padding:10, justifyContent:"space-between"
    },

})
