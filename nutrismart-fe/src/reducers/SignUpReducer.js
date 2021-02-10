import { HANDLE_INPUT_CHANGE, SET_LOADING } from "reducers/Actions";

const defaultFormValues = {
  id: "",
  userName: "",
  email: "",
  password: "",
};

const initialState = {
  isLoading: true,
  form: { ...defaultFormValues },
};

function SignUpReducer(state, action) {
  const { type, payload } = action;

  switch (type) {
    case SET_LOADING:
      return { ...state, isLoading: true };
    case HANDLE_INPUT_CHANGE:
      const { inputName, inputValue } = payload;
      const form = { ...state.form };
      form[inputName] = inputValue;
      return { ...state, form: { ...form } };
    default:
      return { ...state };
  }
}

export { initialState, SignUpReducer };
