import React from 'react';

import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import { createStackNavigator } from '@react-navigation/stack';

import Icon from 'react-native-vector-icons/Ionicons';
import Font from 'react-native-vector-icons/dist/Fontisto'

import HomeScreen from '../screens/tabscreens/HomeScreen';
import ProductsScreen from '../screens/tabscreens/ProductsScreen';
import ChatScreen from "../screens/tabscreens/ChatScreen";
import Fa5 from 'react-native-vector-icons/dist/FontAwesome5'
import {Image} from 'react-native';
import ProductForSearch from '../screens/tabscreens/ProductForSearch';

const CustomStack = createStackNavigator()

const Tab = createMaterialBottomTabNavigator();

const MainTabScreen = () => (
    <Tab.Navigator
        initialRouteName="Home"
        activeColor="#fd5f00"
        screenOptions={{
            tabBarColor : "#fbffc9"
        }}
    >
        <Tab.Screen
            name="Home"
            component={HomeStackScreen}
            options={{
                title : "My Home",
                tabBarLabel: 'Home',
                tabBarIcon: ({ color }) => (
                    <Icon name="ios-home" color={"#fd5f00"} size={26} />
                ),
            }}
        />
        <Tab.Screen
            name="Product"
            component={ProductsScreen}
            options={{
                tabBarLabel: 'Products',
                tabBarIcon: ({ color }) => (
                    <Fa5 name="store" color={"#fd5f00"} size={20} />
                ),
            }}
        />

        <Tab.Screen
            name="Search"
            component={CustomStackScreen}
            options={{
                tabBarLabel: 'Search',
                tabBarIcon: ({ color }) => (
                    <Fa5 name="search" color={"#fd5f00"} size={26} />
                ),
            }}
        />
        <Tab.Screen
            name="Chat"
            component={ChatScreen}
            options={{
                tabBarLabel: 'Chat',
                tabBarIcon: ({ color }) => (
                    <Font name="hipchat" color={"#fd5f00"} size={22} />
                ),
            }}
        />
    </Tab.Navigator>
);

export default MainTabScreen;


const CustomStackScreen = ({navigation}) =>(
    <CustomStack.Navigator screenOptions={{
        headerStyle: {
            backgroundColor: '#fbffc9',
        },
        headerTintColor: '#fd5f00',
        headerTitleStyle: {
            fontWeight: 'bold'
        },
    }}>
        <CustomStack.Screen name={"Search"} component={ProductForSearch} options={{
            headerLeft: () => (
                <Icon.Button name="ios-menu" size={25} color={"#fd5f00"} backgroundColor="#fbffc9" onPress={() => navigation.openDrawer()}/>
            ),
        }}/>
    </CustomStack.Navigator>
)

const HomeStackScreen = ({navigation}) =>(
    <CustomStack.Navigator screenOptions={{
        headerStyle: {
            backgroundColor: '#fbffc9',
        },
        headerTitle: (
            <Image style={{width:100, height:100, resizeMode:"stretch", marginTop:0}} source={require("../../Assets/Logo/logo-1-horizontal.png")}/>
            ),
        headerTintColor: '#fd5f00',
        headerTitleStyle: {
            fontWeight: 'bold'
        }
    }}>
        <CustomStack.Screen name={"Home"} component={HomeScreen} options={{
            headerLeft: () => (
                <Icon.Button name="ios-menu" size={25} color={"#fd5f00"} backgroundColor="#fbffc9" onPress={() => navigation.openDrawer()}/>
            ),

        }}/>
    </CustomStack.Navigator>
)
// const ProductStackScreen = ({navigation}) =>(
//     <CustomStack.Navigator screenOptions={{
//         headerStyle: {
//             backgroundColor: '#fbffc9',
//         },
//         headerTitle: "Categories",
//         headerTintColor: '#fd5f00',
//         headerTitleStyle: {
//             fontWeight: 'bold'
//         }
//     }}>
//         <CustomStack.Screen name={"Home"} component={ProductsScreen} options={{
//             headerLeft: () => (
//                 <Icon.Button name="ios-menu" size={25} color={"#fd5f00"} backgroundColor="#fbffc9" onPress={() => navigation.openDrawer()}/>
//             ),
//
//         }}/>
//     </CustomStack.Navigator>
// )
