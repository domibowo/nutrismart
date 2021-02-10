import {
  HANDLE_FETCH,
  HANDLE_INPUT_QUANTITY,
  HANDLE_CHANGE_VOUCHER,
} from "./Actions";

const defaultFormValues = {
  account: {
    id: "",
    email: "",
    userName: "",
    password: "",
    isActive: "",
  },
  id: "",
  grandTotal: "",
  statusEnum: "",
  trxDate: "",
  transactionDetails: [],
  voucher: {
    id: "",
    name: "",
    valid: "",
    value: "",
  },
  voucherName: "",
};

const defaultFormDetail = {
  id: "",
  price: "",
  quantity: "1",
  product: {
    id: "",
  },
  subtotal: "",
};

const initialStateCart = {
  form: { ...defaultFormValues },
  detail: { defaultFormDetail },
};

function cartReducer(state, action) {
  const { type, payload } = action;
  switch (type) {
    case HANDLE_FETCH:
      return { ...state, form: payload };
    case HANDLE_INPUT_QUANTITY:
      const { idProduct, quantity } = payload;
      const { transactionDetails } = state.form;
      console.log(`ID PRODUCT : `, idProduct);
      const product = transactionDetails.find(
        (detail) => detail.id === idProduct
      );
      console.log(`REDUCER :`, product);
      product["quantity"] = quantity;
      return { ...state, transactionDetails: transactionDetails };
    // case HANDLE_ADD_TO_CART:
    //   return { ...state };
    // case HANDLE_DELETE_CART:
    //   console.log(`ID : `, payload);
    //   let transDetail = state.form.transactionDetails;
    //   transDetail = state.form.transactionDetails.filter(
    //     (detail) => detail.id !== payload
    //   );
    //   console.log(`HASIL FILTER :`, transDetail);
    //   return { ...state.form, transactionDetails: transDetail };
    case HANDLE_CHANGE_VOUCHER:
      const { form } = state;
      form.voucherName = payload;
      return { ...state, form: form };
    default:
      break;
  }
}

export { initialStateCart, cartReducer };
