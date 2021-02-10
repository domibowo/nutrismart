import { HANDLE_FETCH } from "./Actions";

const defaultFormValues = {
  id: "",
  name: "",
  stock: "",
  minBundle: "",
  price: "",
  photo: "",
  productDetail: {
    id: "",
    description: "",
    nutritionFact: "",
    calorie: "",
  },
};

const initialStateProduct = {
  form: { ...defaultFormValues },
  products: [],
};

function productReducer(state, action) {
  const { type, payload } = action;
  switch (type) {
    case HANDLE_FETCH:
      return { ...state, products: payload };
    default:
      break;
  }
}

export { initialStateProduct, productReducer };
