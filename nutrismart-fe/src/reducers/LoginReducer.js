import { HANDLE_INPUT_CHANGE, CLEAR_FORM } from "./Actions";

const defaultFormValues = {
  userName: "",
  password: "",
};

const initialState = {
  isLoading: true,
  form: { ...defaultFormValues },
};

function LoginReducer(state, action) {
  const { type, payload } = action;
  switch (type) {
    case HANDLE_INPUT_CHANGE:
      const { inputName, inputValue } = payload;
      const form = { ...state.form };
      form[inputName] = inputValue;
      return { ...state, form: { ...form } };
    case CLEAR_FORM:
      return { ...state, form: { ...defaultFormValues } };
    default:
      break;
  }
}

export { initialState, LoginReducer };
