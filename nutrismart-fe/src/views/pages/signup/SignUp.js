import React, { useReducer, useState } from "react";

// reactstrap components
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Container,
  Col,
  Form,
} from "reactstrap";

// core components
import TransparentFooter from "components/Footers/TransparentFooter.js";
import { SignUpReducer } from "reducers/SignUpReducer";
import { HANDLE_INPUT_CHANGE } from "reducers/Actions";
import { initialState } from "../../../reducers/SignUpReducer";
import { saveSignUp } from "services/signUpService";
import { Link } from "react-router-dom";

function SignUp() {
  const [state, dispatch] = useReducer(SignUpReducer, initialState);
  const [isChecked, setIsChecked] = useState(false);
  const [lastFocus, setLastFocus] = React.useState(false);
  React.useEffect(() => {
    document.body.classList.add("login-page");
    document.body.classList.add("sidebar-collapse");
    document.documentElement.classList.remove("nav-open");
    window.scrollTo(0, 0);
    document.body.scrollTop = 0;
    return function cleanup() {
      document.body.classList.remove("login-page");
      document.body.classList.remove("sidebar-collapse");
    };
  });

  const handleChangeText = (inputName, inputValue) => {
    dispatch({ type: HANDLE_INPUT_CHANGE, payload: { inputName, inputValue } });
  };

  const handleChangeCheck = () => {
    setIsChecked(!isChecked);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (isChecked) {
      saveSignUp(state.form).then((response) => {
        console.log(response);
      });
    } else {
      window.alert("please agree with term and condition first");
    }
  };

  return (
    <>
      <div className="page-header clear-filter" filter-color="blue">
        <div
          className="page-header-image"
          style={{
            backgroundImage:
              "url(" + require("assets/img/background/bgsignup.jpg") + ")",
          }}
        ></div>
        <div className="content">
          <Container>
            <Col className="ml-auto mr-auto" md="4">
              <Card className="card-login card-plain">
                <Form>
                  <CardHeader className="text-center">
                    <div className="logo-container">
                      <img
                        alt="..."
                        src={require("assets/img/logo/logo-fix.png")}
                      ></img>
                    </div>
                  </CardHeader>
                  <CardBody>
                    <InputGroup
                      className={
                        "no-border input-lg" +
                        (lastFocus ? " input-group-focus" : "")
                      }
                    >
                      <InputGroupAddon addonType="prepend">
                        <InputGroupText>
                          <i className="now-ui-icons text_caps-small"></i>
                        </InputGroupText>
                      </InputGroupAddon>
                      <Input
                        value={state.form.email}
                        required={true}
                        placeholder="Email..."
                        onChange={(event) =>
                          handleChangeText("email", event.target.value)
                        }
                        type="email"
                        onFocus={() => setLastFocus(true)}
                        onBlur={() => setLastFocus(false)}
                      ></Input>
                    </InputGroup>
                    <InputGroup
                      className={
                        "no-border input-lg" +
                        (lastFocus ? " input-group-focus" : "")
                      }
                    >
                      <InputGroupAddon addonType="prepend">
                        <InputGroupText>
                          <i className="now-ui-icons text_caps-small"></i>
                        </InputGroupText>
                      </InputGroupAddon>
                      <Input
                        value={state.form.username}
                        placeholder="Username..."
                        required
                        onChange={(event) =>
                          handleChangeText("userName", event.target.value)
                        }
                        type="text"
                        onFocus={() => setLastFocus(true)}
                        onBlur={() => setLastFocus(false)}
                      ></Input>
                    </InputGroup>
                    <InputGroup
                      className={
                        "no-border input-lg" +
                        (lastFocus ? " input-group-focus" : "")
                      }
                    >
                      <InputGroupAddon addonType="prepend">
                        <InputGroupText>
                          <i className="now-ui-icons text_caps-small"></i>
                        </InputGroupText>
                      </InputGroupAddon>
                      <Input
                        value={state.form.password}
                        required
                        placeholder="Password..."
                        onChange={(event) =>
                          handleChangeText("password", event.target.value)
                        }
                        type="password"
                        onFocus={() => setLastFocus(true)}
                        onBlur={() => setLastFocus(false)}
                      ></Input>
                    </InputGroup>
                  </CardBody>
                  <label>
                    <input
                      type="checkbox"
                      onChange={handleChangeCheck}
                      checked={isChecked}
                    />{" "}
                    I Agree with term and condition.
                  </label>
                  <CardFooter className="text-center">
                    <Button
                      block
                      onClick={(event) => handleSubmit(event)}
                      className="btn-round"
                      type="submit"
                      color="info"
                      size="lg"
                    >
                      Sign Up
                    </Button>
                    <div className="pull-left">
                      <h6>
                        <a className="link" href="/product">
                          Back to Home Page
                        </a>
                      </h6>
                    </div>
                    <div className="pull-right">
                      <h6>
                        <Link to="/product">Sign In</Link>
                      </h6>
                    </div>
                  </CardFooter>
                </Form>
              </Card>
            </Col>
          </Container>
        </div>
        <TransparentFooter />
      </div>
    </>
  );
}

export default SignUp;
