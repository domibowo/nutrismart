import React from 'react';
import { StyleSheet} from 'react-native';
import Categories from "../product/Categories";
import {createStackNavigator} from "@react-navigation/stack";
import Products from "../product/Products";
import AntD from 'react-native-vector-icons/dist/Entypo';
import ConfirmPayment from '../cart/ConfirmPayment';
import VoucherScreen from '../cart/VoucherScreen';
import CartScreens from '../cart/CartScreens';
import Mci from"react-native-vector-icons/MaterialCommunityIcons"
import ScannerScreen from '../qrcode/ScannerScreen';

const Stack = createStackNavigator();
const ProductsScreen = ({navigation}) => {



    return (
            <Stack.Navigator initialRouteName={Categories}>
                <Stack.Screen name={"Categories"} component={Categories} options={{
                    title :"Categories",
                    headerStyle : {
                        backgroundColor :"#fbffc9",
                    },
                    headerTintColor : "#fd5f00",
                    headerTitleAlign:"center",
                    headerRight :()=> (<AntD name={"shopping-bag"} size={26} style={{alignItems:"center", marginLeft:170, marginTop:10, color:"#fd5f00"}} onPress={()=> navigation.navigate("Cart")}/>
                    )
                }}/>
                <Stack.Screen name={"Product"} component={Products}options={{
                    title :"Product",
                    headerStyle : {
                        backgroundColor :"#fbffc9",
                    },
                    headerTintColor : "#fd5f00",
                    headerTitleAlign:"center",
                    headerRight :()=> (<AntD name={"shopping-bag"} size={26} style={{alignItems:"center", marginLeft:170, marginTop:10, color:"#fd5f00"}} onPress={()=> navigation.navigate("Cart")}/>
                    )
                }}/>
                <Stack.Screen name={"Cart"} component={CartScreens}
                options={{
                    title :"Cart",
                    headerStyle : {
                    backgroundColor :"#fbffc9",
                },
                    headerTintColor : "#fd5f00",
                    headerTitleAlign:"center",
                }}/>
                <Stack.Screen name={"ConfirmPayment"} component={ConfirmPayment}
                              options={{
                                  title :"Payment Confirmation",
                                  headerStyle : {
                                      backgroundColor :"#fbffc9",
                                  },
                                  headerTintColor : "#fd5f00",
                                  headerTitleAlign:"center",
                              }}/>
                <Stack.Screen name={"Vouchers"} component={VoucherScreen}
                              options={{
                                  title :"Vouchers",
                                  headerStyle : {
                                      backgroundColor :"#fbffc9",
                                  },
                                  headerTintColor : "#fd5f00",
                                  headerTitleAlign:"center",
                                  headerRight :()=> (<Mci name={"qrcode-scan"} size={26} style={{alignItems:"center", marginRight:10, color:"#fd5f00"}} onPress={()=> navigation.navigate("QRCode")}/>
                                  )
                              }}/>
                <Stack.Screen name={"QRCode"} component={ScannerScreen}
                              options={{
                                  title :"Scanner",
                                  headerStyle : {
                                      backgroundColor :"#fbffc9",
                                  },
                                  headerTintColor : "#fd5f00",
                                  headerTitleAlign:"center",
                              }}/>
            </Stack.Navigator>
    );
};



export default ProductsScreen;

const styles = StyleSheet.create({
    container: {
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
});
