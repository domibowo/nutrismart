import React from 'react';
import { StyleSheet,Text, Image, View,SafeAreaView ,TouchableHighlight} from 'react-native';
import Carousel from 'react-native-snap-carousel';
import VectorIcon from "react-native-vector-icons/FontAwesome";

export default class MyCustomCarousel extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            activeIndex:0,
            carouselItems: [
                {

                    image: <Image source={require('../../../Assets/Banner-1.jpg')}style={{
                        resizeMode:'center',
                        height: 450,
                        width:380,
                    }}/>,

                },

                {

                    image: <Image source={require('../../../Assets/Banner-2.jpg')}style={{
                        resizeMode:'center',
                        width:450,
                        height:380
                    }}/>,

                },
                {

                    image: <Image source={require('../../../Assets/Banner-3.jpg')}style={{
                        resizeMode:'center',
                        width:450,
                        height:380
                    }}/>,

                },
                {

                    image: <Image source={require('../../../Assets/Banner-4.jpg')}style={{
                        resizeMode:'center',
                        width:450,
                        height:380
                    }}/>,

                },
            ]}
    }
    _renderItem({item,index}){
        return (
            <View style={{flex:1, justifyContent:'center',alignItems:'center'}}>
                {/* <Text style={{color:'#fff', fontSize:20,fontStyle: 'italic', fontWeight: 'bold', fontFamily: 'Myriad'}} >{item.upTitle}</Text> */}
                {item.image}
                {/* <Text style={{color:'#fff', fontSize:16,fontStyle: 'italic', fontWeight: 'bold', fontFamily: 'Myriad'}} >{item.title}</Text> */}
            </View>
        )
    }
    render() {
        return (
            <SafeAreaView style={styles.container}>
                <TouchableHighlight
                    onPress={
                        () => { this.carousel._snapToItem(this.state.activeIndex-1)}
                    }>
                    <VectorIcon name="angle-left" size={55} color="#305591" />
                </TouchableHighlight>
                <View>
                    <Carousel
                        ref={ref => this.carousel = ref}
                        data={this.state.carouselItems}
                        sliderWidth={365}
                        itemWidth={365}
                        renderItem={this._renderItem}
                        layout={"tinder"}
                        onSnapToItem = { index => this.setState({activeIndex:index})

                        }
                    />
                </View>
                <TouchableHighlight
                    onPress={
                        () => { this.carousel._snapToItem(this.state.activeIndex+1)}
                    }>
                    <VectorIcon name="angle-right" size={55} color="#305591" />
                </TouchableHighlight>
            </SafeAreaView>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'center',
    },
    text: {
        flex: 1,
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize:34, fontStyle:'italic', fontWeight: 'bold'
    }
});
