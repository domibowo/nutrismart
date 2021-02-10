import React, {useEffect, useReducer} from 'react';
import {ActivityIndicator, ScrollView, View} from 'react-native';
import AsyncStorage from '@react-native-community/async-storage';
import {getProfile} from '../../components/services/ProfileSevice';
import {initialState, ProfileReducer} from '../../components/reducers/ProfileReducer';
import {FETCH_COMPLETE, SET_LOADING} from '../../components/actions/Actions';
import CardProfile from './CardProfile';

export default function ProfileScreen() {

    const [state, dispatch] = useReducer(ProfileReducer, initialState)
    const {profile, isLoading} = state

    const setLoading = () => dispatch({type:SET_LOADING})
    const fetchComplete = (payload) => dispatch ({type:FETCH_COMPLETE, payload})

    const getId = async () =>{
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

    useEffect(()=>{
        getId()
    },[])


    return(
        <View>
                {
                    isLoading ?
                        <View>
                            <ActivityIndicator size={"large"}/>
                        </View> :
                        <ScrollView>
                            <CardProfile profile={profile}/>
                        </ScrollView>
                }
        </View>
    )
}
