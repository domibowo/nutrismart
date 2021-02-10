import React, {useEffect, useReducer} from 'react';
import { View, StyleSheet } from 'react-native';
import {useTheme, Avatar, Title, Drawer, Text, TouchableRipple, Switch} from 'react-native-paper';
import {DrawerContentScrollView, DrawerItem} from '@react-navigation/drawer';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import Ion from 'react-native-vector-icons/dist/Ionicons'
import{ AuthContext } from '../components/context';
import {initialState, ProfileReducer} from '../components/reducers/ProfileReducer';
import {FETCH_COMPLETE, SET_LOADING} from '../components/actions/Actions';
import AsyncStorage from '@react-native-community/async-storage';
import {getProfile} from '../components/services/ProfileSevice';
import AvatarText from 'react-native-paper/src/components/Avatar/AvatarText';

export function DrawerContent(props) {

    const paperTheme = useTheme();

    const [state, dispatch] = useReducer(ProfileReducer, initialState)
    const {profile} = state

    const { signOut, toggleTheme } = React.useContext(AuthContext);

    const fetchComplete = (payload) => dispatch ({type:FETCH_COMPLETE, payload})

    const setLoading = () => dispatch({type:SET_LOADING})

    const getName =async () =>{
        try {
            const id = await AsyncStorage.getItem('userToken')
            setLoading()
            getProfile(id)
                .then((profile)=>{
                    fetchComplete(profile)
                })

        }catch (e) {
            console.log(e, 'error di profile');
        }
    }

    const labelName = () => {
        const firstName = profile.firstName
        const firstLetter = firstName.split("")
        const lastName = profile.lastName
        const lastLetter = lastName.split("")
        return firstLetter[0]+lastLetter[0]
    }

    useEffect(()=>{
        getName()
    },[])

    return(
        <View style={{flex:1}}>
            <DrawerContentScrollView {...props}>
                <View style={styles.drawerContent}>
                    <View style={styles.userInfoSection}>
                        <View style={{flexDirection:'row',marginTop: 15}}>
                            <AvatarText style={{backgroundColor:"#ffa34d"}} label={labelName()}/>
                            <View style={{marginLeft:15, flexDirection:'column'}}>
                                <Title style={styles.title}>{profile.firstName} {profile.lastName}</Title>
                            </View>
                        </View>
                    </View>

                    <Drawer.Section style={styles.drawerSection}>
                        <DrawerItem
                            icon={({color, size}) => (
                                <Icon
                                    name="account-outline"
                                    color={color}
                                    size={size}
                                />
                            )}
                            label="Profile"
                            onPress={() => {props.navigation.navigate('ProfileScreen')}}
                        />
                        <DrawerItem
                            icon={({color, size}) => (
                                <Ion
                                    name="md-paper"
                                    color={color}
                                    size={size}
                                />
                            )}
                            label="History"
                            onPress={() => {props.navigation.navigate('HistoryScreen')}}
                        />
                        <DrawerItem
                            icon={({color, size}) => (
                                <Icon
                                    name="settings-outline"
                                    color={color}
                                    size={size}
                                />
                            )}
                            label="Settings"
                            onPress={() => {props.navigation.navigate('SettingScreen')}}
                        />

                    </Drawer.Section>
                    <Drawer.Section title="Preferences">
                        <TouchableRipple onPress={() => {toggleTheme()}}>
                            <View style={styles.preference}>
                                <Text>Dark Theme</Text>
                                <View pointerEvents="none">
                                    <Switch value={paperTheme.dark}/>
                                </View>
                            </View>
                        </TouchableRipple>
                    </Drawer.Section>
                </View>
            </DrawerContentScrollView>
            <Drawer.Section style={styles.bottomDrawerSection}>
                <DrawerItem
                    icon={({color, size}) => (
                        <Icon
                            name="exit-to-app"
                            color={color}
                            size={size}
                        />
                    )}
                    label="Sign Out"
                    onPress={() => {signOut()}}
                />
            </Drawer.Section>
        </View>
    );
}

const styles = StyleSheet.create({
    drawerContent: {
        flex: 1,
    },
    userInfoSection: {
        paddingLeft: 20,
    },
    title: {
        fontSize: 16,
        marginTop: 3,
        fontWeight: 'bold',
    },
    caption: {
        fontSize: 14,
        lineHeight: 14,
    },
    row: {
        marginTop: 20,
        flexDirection: 'row',
        alignItems: 'center',
    },
    section: {
        flexDirection: 'row',
        alignItems: 'center',
        marginRight: 15,
    },
    paragraph: {
        fontWeight: 'bold',
        marginRight: 3,
    },
    drawerSection: {
        marginTop: 15,
    },
    bottomDrawerSection: {
        marginBottom: 15,
        borderTopColor: '#f4f4f4',
        borderTopWidth: 1
    },
    preference: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingVertical: 12,
        paddingHorizontal: 16,
    },
});
