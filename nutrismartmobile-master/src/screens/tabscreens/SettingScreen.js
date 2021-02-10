import React, {useContext, useEffect, useState} from 'react';
import {View, Text,  StyleSheet, Alert, Modal} from 'react-native';
import { Button,Switch} from 'react-native-paper';
import AsyncStorage from '@react-native-community/async-storage';
import {putProfile} from '../../components/services/ProfileSevice';
import Ant from 'react-native-vector-icons/AntDesign';
import {AuthContext} from '../../components/context';

const SettingsScreen = () => {

    const [isEnabled, setIsEnabled] = useState(false);
    const toggleSwitch = () => setIsEnabled(previousState => !previousState);

    const {signOut} = useContext(AuthContext)

    const [visible, setVisible] = useState(false)

    const onOpen=()=>{
        setVisible(true)
    }

    const onClose=()=>{
        setVisible(false)
    }

    const getId = async () =>{
        const id = await AsyncStorage.getItem('userToken')
        putProfile(id)
            .then((response)=>{
                toggleSwitch()
                onClose()
                signOut()
            })
    }

    return (
        <View style={styles.container}>
            <Text>NonActive Account</Text>
            <Switch
                trackColor={{ false: "#767577", true: "#81b0ff" }}
                thumbColor={isEnabled ? "#f5dd4b" : "#f4f3f4"}
                ios_backgroundColor="#3e3e3e"
                onValueChange={onOpen}
                value={isEnabled}
            />
            <Modal visible={visible} animationType="fade" transparent={true}
            >
                <View style={styles.modalView}>
                    <Text>Are you sure to NonActive your Account?</Text>
                    <View style={{flexDirection:"row", marginTop:20}}>
                    <Button onPress={getId} mode={"text"}>
                        <Ant name={"check"} size={24}/>
                    </Button>
                    <Button onPress={onClose} mode={"text"}>
                        <Ant name={"close"} size={24}/>
                    </Button>
                    </View>
                </View>
            </Modal>
        </View>
    );
};

export default SettingsScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'center'
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
});
