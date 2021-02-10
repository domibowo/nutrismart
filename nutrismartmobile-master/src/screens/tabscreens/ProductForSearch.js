import React, {Component} from 'react';
import {
    Text,
    StyleSheet,
    View,
    FlatList,
    TextInput,
    ActivityIndicator,
    Alert, TouchableOpacity,
} from 'react-native';
import {getProducts} from '../../components/services/ProductService';
import CardProduct from '../product/CardProduct';

export default class ProductForSearch extends Component {
    constructor(props) {
        super(props);
        this.state = { isLoading: true, text: '' };
        this.arrayholder = [];
    }

    getAllProduct = () => {
        getProducts()
            .then((products)=>{
                this.setState({ isLoading: false})
                this.arrayholder=products
            })
    }

    componentDidMount() {
        this.getAllProduct()
    }

    SearchFilterFunction(text) {
        const newData = this.arrayholder.filter(function(item) {
            const itemData = item.name ? item.name.toUpperCase() : ''.toUpperCase();
            const textData = text.toUpperCase();
            return itemData.indexOf(textData) > -1;
        });
        this.setState({
            dataSource: newData,
            text: text,
        });
    }

    render() {
        if (this.state.isLoading) {
            return (
                <View style={{ flex: 1, paddingTop: 20 }}>
                    <ActivityIndicator />
                </View>
            );
        }
        return (
            <View style={styles.viewStyle}>
                <TextInput
                    style={styles.textInputStyle}
                    onChangeText={text => this.SearchFilterFunction(text)}
                    value={this.state.text}
                    underlineColorAndroid="transparent"
                    placeholder="Search Product"
                />
                <FlatList
                    data={this.state.dataSource}
                    renderItem={({ item }) => (
                        <View>
                            <CardProduct product={item}/>
                        </View>
                    )}
                    enableEmptySections={true}
                    style={{ marginTop: 10 }}
                    keyExtractor={(item, index) => index.toString()}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    viewStyle: {
        justifyContent: 'center',
        flex: 1,
        marginTop: 10,
        padding: 16,
    },
    textStyle: {
        padding: 10,
    },
    textInputStyle: {
        height: 40,
        borderWidth: 1,
        paddingLeft: 10,
        borderColor: 'gainsboro',
        backgroundColor: '#FFFFFF',
    },
});
