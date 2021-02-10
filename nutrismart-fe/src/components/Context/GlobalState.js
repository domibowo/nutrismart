import React, { useReducer, useEffect } from "react";
import ShopContext from "./ShopContext";
import { initialStateCart } from "reducers/cartReducer";
import { cartReducer } from "reducers/cartReducer";
import { HANDLE_ADD_TO_CART } from "reducers/Actions";
import { HANDLE_DELETE_CART } from "reducers/Actions";
import { getCart } from "services/cartService";
import { useCookies } from "react-cookie";
import { HANDLE_FETCH } from "reducers/Actions";

const GlobalState = (props) => {
  const [cartState, cartDispatch] = useReducer(cartReducer, initialStateCart);
  const [cookies] = useCookies(["id"]);

  useEffect(() => {
    loadCart();
  }, cartState);

  const loadCart = () => {
    getCart(cookies.id).then((response) => {
      cartDispatch({ type: HANDLE_FETCH, payload: response.data });
    });
  };

  const addProductToCart = (productId) => {
    cartDispatch({ type: HANDLE_ADD_TO_CART, payload: productId });
  };

  const removeProductFromCart = (productId) => {
    cartDispatch({ type: HANDLE_DELETE_CART, payload: productId });
  };

  return (
    <ShopContext.Provider
      value={{
        cartState: cartState,
        cartDispatch: cartDispatch,
        addProductToCart: addProductToCart,
        removeProductFromCart: removeProductFromCart,
      }}
    >
      {props.children}
    </ShopContext.Provider>
  );
};

export default GlobalState;
