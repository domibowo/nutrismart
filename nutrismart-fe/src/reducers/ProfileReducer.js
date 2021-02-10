import {
  HANDLE_INPUT_CHANGE_ACCOUNT,
  CLEAR_FORM,
  HANDLE_INPUT_CHANGE_PROFILE,
  HANDLE_FETCH,
} from "./Actions";

const defaultFormValues = {
  id: "",
  userName: "",
  email: "",
  password: "",
  profile: {
    id: "",
    firstName: "",
    lastName: "",
    gender: "",
    birthDate: "",
    phone: "",
    status: "",
    address: "",
    detail: "",
    latitude: "",
    longitude: "",
  },
};

const initialState = {
  isLoading: true,
  form: { ...defaultFormValues },
};

function ProfileReducer(state, action) {
  const { type, payload } = action;
  const { form } = state;
  switch (type) {
    case HANDLE_INPUT_CHANGE_ACCOUNT:
      const { inputName, inputValue } = payload;
      form[inputName] = inputValue;
      return { ...state, form: form };
    case HANDLE_INPUT_CHANGE_PROFILE:
      const { profile } = form;
      const { profileName, profileValue } = payload;
      profile[profileName] = profileValue;
      return { ...state, profile: profile };
    case CLEAR_FORM:
      return { ...state, form: { ...defaultFormValues } };
    case HANDLE_FETCH:
      return {
        ...state,
        form: payload,
      };
    default:
      return { ...state };
  }
}

export { initialState, ProfileReducer };
