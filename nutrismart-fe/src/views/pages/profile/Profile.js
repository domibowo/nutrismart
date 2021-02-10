import React, { useReducer, useState } from "react";

// reactstrap components
import {
  Button,
  NavItem,
  NavLink,
  Nav,
  TabContent,
  TabPane,
  Container,
  Col,
  Card,
  CardHeader,
  CardBody,
  InputGroup,
  InputGroupAddon,
  InputGroupText,
  Input,
  Modal,
  ModalHeader,
  ModalBody,
} from "reactstrap";

// core components
import ProfilePageHeader from "components/Headers/ProfilePageHeader.js";
import DefaultFooter from "components/Footers/DefaultFooter.js";
import { ProfileReducer, initialState } from "reducers/ProfileReducer";
import Datetime from "react-datetime";
import { useCookies } from "react-cookie";
import NavbarProduct from "views/pages/product/NavbarProduct";
import { getProfile } from "services/profileService";
import { HANDLE_FETCH } from "reducers/Actions";
import "react-dropdown/style.css";
import { HANDLE_INPUT_CHANGE_ACCOUNT } from "reducers/Actions";
import { HANDLE_INPUT_CHANGE_PROFILE } from "reducers/Actions";
import { updateProfile } from "services/profileService";
import Moment from "moment";
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng,
} from "react-places-autocomplete";

function Profile() {
  const [iconPills, setIconPills] = React.useState(1);
  const [lastFocus, setLastFocus] = React.useState(false);
  const [state, dispatch] = useReducer(ProfileReducer, initialState);
  const [cookies] = useCookies(["id"]);
  const [showHidePassword, setShowHidePassword] = useState(true);
  const [iconPassword, setIconPassword] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [address, setAddress] = useState("");

  React.useEffect(() => {
    document.body.classList.add("profile-page");
    document.body.classList.add("sidebar-collapse");
    document.documentElement.classList.remove("nav-open");
    handleLoad();
    return function cleanup() {
      document.body.classList.remove("profile-page");
      document.body.classList.remove("sidebar-collapse");
    };
  }, []);

  const handleNextButton = () => {
    setIconPills(iconPills + 1);
  };

  const handleLoad = () => {
    if (cookies.id === "undefined") {
      setModalOpen(true);
    } else {
      setModalOpen(false);
      getProfile(cookies.id).then((response) => {
        console.log(`RESPONSE PROFILE : `, response);
        dispatch({ type: HANDLE_FETCH, payload: response.data });
      });
    }
  };

  const clickShowHidePassword = () => {
    setIconPassword(!iconPassword);
    setShowHidePassword(!showHidePassword);
  };

  const handleChangeTextAccount = (inputName, inputValue) => {
    dispatch({
      type: HANDLE_INPUT_CHANGE_ACCOUNT,
      payload: { inputName, inputValue },
    });
  };

  const handleChangeTextProfile = (profileName, profileValue) => {
    dispatch({
      type: HANDLE_INPUT_CHANGE_PROFILE,
      payload: { profileName, profileValue },
    });
  };

  const handleSubmit = () => {
    console.log(`PARAMSS : `, state.form);
    updateProfile(state.form).then((response) => {
      console.log(`RESPONSE UPDATE`, response);
    });
  };

  const handleChange = (address) => {
    handleChangeTextProfile("address", address);
  };

  const handleSelect = (address) => {
    geocodeByAddress(address)
      .then((results) => (this.setState.latitude = getLatLng(results[0])))
      .then((latLng) => console.log("Success", latLng))
      .catch((error) => console.error("Error", error));
  };

  return (
    <>
      {console.log(state.form)}
      <NavbarProduct />
      <div className="wrapper">
        <ProfilePageHeader dataProfile={state.form} />
        <div className="section">
          <Container>
            <Col className="ml-auto mr-auto" md="10" xl="10">
              <Card>
                <CardHeader>
                  <Nav className="justify-content-center" role="tablist" tabs>
                    <NavItem>
                      <NavLink
                        className={iconPills === 1 ? "active" : ""}
                        href="#pablo"
                        onClick={(e) => {
                          e.preventDefault();
                          setIconPills(1);
                        }}
                      >
                        <i className="now-ui-icons objects_umbrella-13"></i>
                        Account
                      </NavLink>
                    </NavItem>
                    <NavItem>
                      <NavLink
                        className={iconPills === 2 ? "active" : ""}
                        href="#pablo"
                        onClick={(e) => {
                          e.preventDefault();
                          setIconPills(2);
                        }}
                      >
                        <i className="now-ui-icons shopping_cart-simple"></i>
                        Profile
                      </NavLink>
                    </NavItem>
                    <NavItem>
                      <NavLink
                        className={iconPills === 3 ? "active" : ""}
                        href="#pablo"
                        onClick={(e) => {
                          e.preventDefault();
                          setIconPills(3);
                        }}
                      >
                        <i className="now-ui-icons shopping_shop"></i>
                        Address
                      </NavLink>
                    </NavItem>
                  </Nav>
                </CardHeader>
                <CardBody>
                  <TabContent
                    className="text-center"
                    activeTab={"iconPills" + iconPills}
                  >
                    <TabPane tabId="iconPills1">
                      <CardBody>
                        <InputGroup
                          className={"no-border input-lg input-group-focus"}
                        >
                          <InputGroupAddon addonType="prepend">
                            <InputGroupText>
                              <i className="now-ui-icons text_caps-small"></i>
                            </InputGroupText>
                          </InputGroupAddon>
                          <Input
                            value={state.form.email}
                            placeholder="Email..."
                            disabled
                            onChange={(event) =>
                              handleChangeTextAccount(
                                "email",
                                event.target.value
                              )
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
                            value={state.form.userName}
                            placeholder="Username..."
                            onChange={(event) =>
                              handleChangeTextAccount(
                                "userName",
                                event.target.value
                              )
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
                            placeholder="Password..."
                            onChange={(event) =>
                              handleChangeTextAccount(
                                "password",
                                event.target.value
                              )
                            }
                            type={showHidePassword ? "password" : "text"}
                            onFocus={() => setLastFocus(true)}
                            onBlur={() => setLastFocus(false)}
                          ></Input>
                          <InputGroupAddon
                            addonType="prepend"
                            style={{ paddingLeft: -10 }}
                          >
                            <InputGroupText>
                              <span
                                style={{ alignSelf: "center" }}
                                onClick={(event) => {
                                  clickShowHidePassword(event);
                                }}
                                class={
                                  iconPassword
                                    ? "fa fa-fw fa-eye field-icon toggle-password"
                                    : "fa fa-fw fa-eye fa-eye-slash field-icon toggle-password"
                                }
                              ></span>
                            </InputGroupText>
                          </InputGroupAddon>
                        </InputGroup>
                      </CardBody>
                      <Button
                        className="btn btn-info"
                        style={{ float: "right" }}
                        onClick={() => {
                          handleNextButton();
                        }}
                      >
                        Next
                      </Button>
                    </TabPane>
                    <TabPane tabId="iconPills2">
                      <CardBody>
                        <InputGroup
                          className={"no-border input-lg input-group-focus"}
                        >
                          <InputGroupAddon addonType="prepend">
                            <InputGroupText>
                              <i className="now-ui-icons text_caps-small"></i>
                            </InputGroupText>
                          </InputGroupAddon>
                          <Input
                            value={state.form.profile.firstName}
                            placeholder="firstname..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "firstName",
                                event.target.value
                              )
                            }
                            type="text"
                            onFocus={() => setLastFocus(true)}
                            onBlur={() => setLastFocus(false)}
                          >
                            {" "}
                            <i className="now-ui-icons text_caps-small"></i>
                          </Input>
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
                            value={state.form.profile.lastName}
                            placeholder="lastname..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "lastName",
                                event.target.value
                              )
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
                          <select
                            class="form-control form-control-lg"
                            value={state.form.profile.gender}
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "gender",
                                event.target.value
                              )
                            }
                          >
                            <option disabled selected>
                              SELECT GENDER
                            </option>
                            <option>MALE</option>
                            <option>FEMALE</option>
                          </select>
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
                          <Datetime
                            value={state.form.profile.birthDate}
                            onChange={(event) => {
                              handleChangeTextProfile(
                                "birthDate",
                                event.format("DD/MM/YYYY")
                              );
                            }}
                            className="form-control"
                            timeFormat={false}
                          />
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
                            value={state.form.profile.phone}
                            placeholder="phone..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "phone",
                                event.target.value
                              )
                            }
                            type="text"
                            onFocus={() => setLastFocus(true)}
                            onBlur={() => setLastFocus(false)}
                          ></Input>
                        </InputGroup>
                      </CardBody>
                      <Button
                        className="btn btn-info"
                        style={{ float: "right" }}
                        onClick={() => {
                          handleNextButton();
                        }}
                      >
                        Next
                      </Button>
                    </TabPane>
                    <TabPane tabId="iconPills3">
                      <CardBody>
                        <InputGroup
                          style={{ position: "static" }}
                          className={"no-border input-lg input-group-focus"}
                        >
                          <InputGroupAddon addonType="prepend">
                            <InputGroupText>
                              <i className="now-ui-icons text_caps-small"></i>
                            </InputGroupText>
                          </InputGroupAddon>

                          <PlacesAutocomplete
                            value={state.form.profile.address}
                            onChange={handleChange}
                            onSelect={handleSelect}
                          >
                            {({
                              getInputProps,
                              suggestions,
                              getSuggestionItemProps,
                              loading,
                            }) => (
                              <>
                                <Input
                                  {...getInputProps({
                                    placeholder: "Address ...",
                                    className: "location-search-input",
                                  })}
                                ></Input>
                                <br />
                                <div className="autocomplete-dropdown-container">
                                  {loading && <div>Loading...</div>}
                                  {suggestions.map((suggestion) => {
                                    const className = suggestion.active
                                      ? "suggestion-item--active"
                                      : "suggestion-item";
                                    // inline style for demonstration purpose
                                    const style = suggestion.active
                                      ? {
                                          backgroundColor: "#fafafa",
                                          cursor: "pointer",
                                        }
                                      : {
                                          backgroundColor: "#ffffff",
                                          cursor: "pointer",
                                        };
                                    return (
                                      <div
                                        {...getSuggestionItemProps(suggestion, {
                                          className,
                                          style,
                                        })}
                                      >
                                        <div style={{ position: "relative" }}>
                                          {suggestion.description}
                                        </div>
                                      </div>
                                    );
                                  })}
                                </div>
                              </>
                            )}
                          </PlacesAutocomplete>
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
                            value={state.form.profile.detail}
                            placeholder="Detail..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "detail",
                                event.target.value
                              )
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
                            value={state.form.profile.latitude}
                            placeholder="Latitude..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "latitude",
                                event.target.value
                              )
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
                            value={state.form.profile.longitude}
                            placeholder="Longitude..."
                            onChange={(event) =>
                              handleChangeTextProfile(
                                "longitude",
                                event.target.value
                              )
                            }
                            type="text"
                            onFocus={() => setLastFocus(true)}
                            onBlur={() => setLastFocus(false)}
                          ></Input>
                        </InputGroup>
                      </CardBody>
                      <Button
                        className={"btn btn-success"}
                        style={{ float: "right" }}
                        onClick={() => {
                          handleSubmit();
                        }}
                      >
                        SAVE
                      </Button>
                    </TabPane>
                  </TabContent>
                </CardBody>
              </Card>
            </Col>
          </Container>
          <Container>
            <Modal isOpen={modalOpen} size="md" centered>
              <ModalHeader toggle={() => {}} tag="strong">
                You Should Login !!!
              </ModalHeader>
              <ModalBody style={{ marginBottom: 20, alignSelf: "center" }}>
                <Button
                  type="button"
                  style={{ alignSelf: "center" }}
                  color="info"
                  onClick={() => {
                    window.location.href = "/product";
                  }}
                >
                  Login Page
                </Button>
              </ModalBody>
            </Modal>
          </Container>
        </div>
        <DefaultFooter />
      </div>
    </>
  );
}

export default Profile;
