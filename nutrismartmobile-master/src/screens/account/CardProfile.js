import React, {useEffect, useReducer, useState} from 'react';
import {View, StyleSheet, SafeAreaView, Image} from 'react-native';
import DatePicker from 'react-native-datepicker';
import {Text, TextInput, Button} from 'react-native-paper';
import {useForm} from 'react-hook-form';
import {postProfile} from '../../components/services/ProfileSevice';
import AsyncStorage from '@react-native-community/async-storage';
import {getAccount} from '../../components/services/AccountService';
import GooglePlacesAutocomplete from 'react-native-google-places-autocomplete';

function CardProfile(props) {

    const [startDate, setStartDate] = useState(new Date());
    const {profile} = props

    const {register, setValue, handleSubmit, watch}=useForm({
        defaultValues:{
            address : profile.address,
            birthDate : profile.birthDate,
            detail : profile.detail,
            firstName : profile.firstName,
            gender : profile.gender,
            lastName : profile.lastName,
            latitude : profile.latitude,
            longitude : profile.longitude,
            phone : profile.phone,
            status : profile.status
        }
    })

    const saveProfile = async (profile) =>{
        const accountID = await AsyncStorage.getItem("userToken")
        getAccount(accountID)
            .then((account)=>{
                const {id, userName, email, password, isActive} = account
                const form = {id:id, userName:userName, email:email, password:password, isActive:isActive, profile: profile}
                postProfile(form)
                    .then((response)=>{
                        props.navigation.navigate('Home')
                    })
            })
    }

    const defaultValues = watch()

    useEffect(()=>{
        register('firstName')
        register('lastName')
        register('gender')
        register('birthDate')
        register('phone')
        register('address')
        register('detail')
    },[register])

    return(
        <React.Fragment>
        <SafeAreaView style={styles.container}>
            <View style={{alignItems:"center", marginBottom:15}}>
                <Image source={require("../../../Assets/Logo/logo-1-horizontal.png")} style={{width:130, height:50}}/>
            </View>
            <TextInput
                defaultValues={profile.firstName}
                label="First Name"
                style={{marginTop:5, marginBottom:5}}
                value={defaultValues.firstName}
                onChangeText={(text)=>{setValue('firstName', text)}}
            />
            <TextInput
                defaultValue={profile.lastName}
                label="Last Name"
                value={defaultValues.lastName}
                style={{marginTop:5, marginBottom:5}}
                onChangeText={(text)=>{setValue('lastName', text)}}
            />
            <TextInput
                defaultValue={profile.gender}
                label="Gender"
                value={defaultValues.gender}
                style={{marginTop:5, marginBottom:5}}
                onChangeText={(text)=>{setValue('gender', text)}}
            />
            <DatePicker
                style={{ width: 200, marginTop:5, marginBottom:5 }}
                selected={startDate}
                onChange={date => setStartDate(date)}
                placeholder="select date"
                format="DD-MM-YYYY"
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                customStyles={{
                    dateIcon: {
                        position: 'absolute',
                        left: 0,
                        top: 4,
                        marginLeft: 0
                    },
                    dateInput: {
                        marginLeft: 36
                    }
                }}
            />
            <TextInput
                label="Phone Number"
                value={defaultValues.phone}
                onChangeText={(text)=>{setValue('phone', text)}}
            />
            <GooglePlacesAutocomplete
                placeholder={"Address"}
                query={{key:"AIzaSyB7jbVjgBd0Ueyn49tj0Zzgp0EsRrHwJgQ",
                language:'en',
                types:'geocode'}}
                minLength={2}
                fetchDetails={true}
                listViewDisplayed={'auto'}
                onChangeText={(text)=>setValue('address', text)}
                currentLocation={false}
                onPress={(data, details = null) => {
                    console.log(details, 'details');
                    setValue('address',data)
                }}
                textInputProps={(text)=> setValue('address', text)}/>
            <TextInput
                defaultValue={profile.detail}
                label="Detail"
                value={defaultValues.detail}
                style={{marginTop:5, marginBottom:5}}
                onChangeText={(text)=>{setValue('detail', text)}}
            />

            <TextInput
                defaultValue={profile.status}
                value={defaultValues.status}
                style={{marginTop:5, marginBottom:5}}
                onChangeText={(text)=>{setValue('status', text)}}
                disabled={true}
            />
            <View style={styles.clickButton}>
                <Button
                    mode={"contained"}
                    color={"#ffa34d"}
                    onPress={handleSubmit(saveProfile)}
                >Submit</Button>
            </View>
        </SafeAreaView >
        </React.Fragment>
    )
}

export default CardProfile

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
        padding: 20,
        backgroundColor: '#fff',
    },
    clickButton: {
        width: '100%',
        marginTop: 50,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 10
    },
    text_footer: {
        color: '#05375A',
        fontSize: 18
    },
});
