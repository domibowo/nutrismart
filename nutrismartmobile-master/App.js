import React, { useEffect } from 'react';
import { View, ActivityIndicator } from 'react-native';
import {
  NavigationContainer,
  DefaultTheme as NavigationDefaultTheme,
  DarkTheme as NavigationDarkTheme
} from '@react-navigation/native';
import { createDrawerNavigator } from '@react-navigation/drawer';

import {
  Provider as PaperProvider,
  DefaultTheme as PaperDefaultTheme,
  DarkTheme as PaperDarkTheme
} from 'react-native-paper';

import { DrawerContent } from './src/routes/DrawerContent';

import MainTabScreen from './src/routes/MainTabScreen';
import SettingsScreen from './src/screens/tabscreens/SettingScreen';

import { AuthContext } from './src/components/context';

import RootStackScreen from './src/routes/RootStackScreen';

import AsyncStorage from '@react-native-community/async-storage';
import {initialLoginState, LoginReducer} from './src/components/reducers/LoginReducer';
import {LOGIN, LOGOUT, RETRIEVE_TOKEN} from './src/components/actions/Actions';
import HistoryScreen from './src/screens/transaction/HistoryScreen';
import ProfileScreen from './src/screens/account/ProfileScreen';

const Drawer = createDrawerNavigator();


const App = () => {

  const [isDarkTheme, setIsDarkTheme] = React.useState(false);

  const CustomDefaultTheme = {
    ...NavigationDefaultTheme,
    ...PaperDefaultTheme,
    colors: {
      ...NavigationDefaultTheme.colors,
      ...PaperDefaultTheme.colors,
      background: '#ffffff',
      text: '#333333'
    }
  }

  const CustomDarkTheme = {
    ...NavigationDarkTheme,
    ...PaperDarkTheme,
    colors: {
      ...NavigationDarkTheme.colors,
      ...PaperDarkTheme.colors,
      background: '#333333',
      text: '#ffffff'
    }
  }

  const theme = isDarkTheme ? CustomDarkTheme : CustomDefaultTheme;

  const [loginState, dispatch] = React.useReducer(LoginReducer, initialLoginState);

  const {userToken, isLoading} = loginState

  const setLogin = (payload) => dispatch({ type: LOGIN, payload})
  const setLogout = () => dispatch({type:LOGOUT})
  const retrieveToken = (payload)=>dispatch({type: RETRIEVE_TOKEN, payload})

  const authContext = React.useMemo(() => ({

    signIn: async(foundUser) => {
      const userToken = String(foundUser[0].userToken);
      const userName = foundUser[0].username;

      try {
        await AsyncStorage.setItem('userToken', userToken);
        console.log(userToken, 'usertoken');
      } catch(e) {
        console.log(e,'signIn');
      }
      setLogin({userName: userName, token: userToken})
    },

    signOut: async() => {
      try {
        await AsyncStorage.removeItem('userToken');
        await AsyncStorage.removeItem('cart')
      } catch(e) {
        console.log(e, 'signOut');
      }
      setLogout();
    },
    toggleTheme: () => {
      setIsDarkTheme( isDarkTheme => !isDarkTheme );
    }
  }), []);

  useEffect(() => {
    setTimeout(async() => {
      let userToken;
      userToken = null;
      try {
        userToken = await AsyncStorage.getItem('userToken');
        console.log(userToken, 'diappjs');
      } catch(e) {
        console.log(e);
      }
      retrieveToken(userToken)
    }, 1000);
  }, []);

  if( isLoading ) {
    return(
        <View style={{flex:1,justifyContent:'center',alignItems:'center'}}>
          <ActivityIndicator size="large"/>
        </View>
    );
  }
  return (
      <PaperProvider theme={theme}>
        <AuthContext.Provider value={authContext}>
          <NavigationContainer theme={theme}>
            { userToken !== null ? (
                    <Drawer.Navigator drawerContent={props => <DrawerContent {...props} />}>
                      <Drawer.Screen name="HomeDrawer" component={MainTabScreen} />
                      <Drawer.Screen name="ProfileScreen" component={ProfileScreen} />
                      <Drawer.Screen name="SettingScreen" component={SettingsScreen} />
                      <Drawer.Screen name="HistoryScreen" component={HistoryScreen} />
                    </Drawer.Navigator>
                )
                :
                <RootStackScreen/>
            }
          </NavigationContainer>
        </AuthContext.Provider>
      </PaperProvider>
  );
}

export default App;
