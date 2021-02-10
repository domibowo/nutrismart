import React, { useState } from "react";
import { Link } from "react-router-dom";
// reactstrap components
import {
  Collapse,
  NavbarBrand,
  Navbar,
  NavItem,
  NavLink,
  Nav,
  Container,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  Label,
  Col,
  Row,
} from "reactstrap";
import Logo from "../../../assets/img/logo/logo-fix.png";
import { useCookies } from "react-cookie";
import { ListCart } from "components/Card/Cart/ListCart";
import { HANDLE_INPUT_QUANTITY } from "reducers/Actions";
import ShopContext from "components/Context/ShopContext";
import { deleteCart } from "services/cartService";
import { updateCart } from "services/cartService";
import { numberFormat } from "shared/currency/Currency";
import { getVoucher } from "services/voucherService";
import { ListVoucher } from "components/Card/Voucher/ListVoucher";
import {
  setVoucherToTransaction,
  commitTransaction,
} from "../../../services/cartService";
function IndexNavbar(props) {
  const [navbarColor, setNavbarColor] = useState("navbar-transparent");
  const [collapseOpen, setCollapseOpen] = useState(false);
  const [navColor, setNavColor] = useState("#fffff");
  const { setModalOpen } = props;
  const [cookies, removeCookie] = useCookies(["id"]);
  const [modalCart, setModalCart] = useState(false);
  const cartContext = React.useContext(ShopContext);
  const [showUpdateButton, setShowUpdateButton] = useState(false);
  const [voucher, setVoucher] = useState([]);
  const [modalVoucher, setModalVoucher] = useState(false);
  const [modalCommitTransaction, setModalCommitTrasaction] = useState(false);

  React.useEffect(() => {
    loadVoucher();
    const updateNavbarColor = () => {
      if (
        document.documentElement.scrollTop > 399 ||
        document.body.scrollTop > 399
      ) {
        setNavbarColor("");
        setNavColor("#121212");
      } else if (
        document.documentElement.scrollTop < 400 ||
        document.body.scrollTop < 400
      ) {
        setNavColor("#ffffff");
        setNavbarColor("navbar-transparent");
      }
    };
    window.addEventListener("scroll", updateNavbarColor);
    return function cleanup() {
      window.removeEventListener("scroll", updateNavbarColor);
    };
  }, []);

  const loadVoucher = () => {
    getVoucher().then((response) => {
      setVoucher(response.data.content);
    });
  };

  const openSignIn = () => {
    setCollapseOpen(false);
    setModalOpen(true);
  };

  const openCart = () => {
    setModalCart(true);
  };

  const onChangeQuantity = (idProduct, quantity) => {
    cartContext.cartDispatch({
      type: HANDLE_INPUT_QUANTITY,
      payload: { idProduct, quantity },
    });
    setShowUpdateButton(true);
  };

  const handleLogout = (event) => {
    event.preventDefault();
    removeCookie("id");
    window.location.href = "/product";
  };

  const closeCartModal = () => {
    setModalCart(false);
  };

  const handleDeleteCart = (idProduct) => {
    console.log(`MASUK METHOD DELETE`);
    const idTrans = {
      id: cartContext.cartState.form.id,
    };
    deleteCart(idProduct, idTrans).then((response) => {
      console.log(`RESPONSE DELETE : `, response);
    });
  };

  const generateCart = () => {
    if (
      cartContext.cartState.form === "" ||
      cartContext.cartState.form === undefined
    ) {
      return <Label>Sorry you don't have a cart </Label>;
    } else {
      return (
        <>
          {cartContext.cartState.form.transactionDetails.map(
            (detail, index) => {
              return (
                <ListCart
                  key={index}
                  quantity={onChangeQuantity}
                  data={detail}
                  removeCart={handleDeleteCart}
                />
              );
            }
          )}
        </>
      );
    }
  };

  const handleQuantityCart = () => {
    if (cartContext.cartState.form.transactionDetails === undefined) {
      return <Label>0</Label>;
    } else {
      return (
        <Label>{cartContext.cartState.form.transactionDetails.length}</Label>
      );
    }
  };

  const handleNavBar = () => {
    if (cookies.id === undefined || cookies.id === "undefined") {
      return (
        <>
          <NavItem>
            <NavLink
              style={styles.subNavbar}
              onClick={() => {
                openSignIn();
              }}
            >
              <p style={{ color: navColor }}>Sign In</p>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={styles.subNavbar}>
              <Link to="/signup">
                <p style={{ color: navColor }}>Sign Up</p>
              </Link>
            </NavLink>
          </NavItem>
        </>
      );
    } else {
      return (
        <>
          <NavItem style={styles.navbar}>
            <NavLink
              onClick={() => {
                openCart();
              }}
            >
              <p style={{ color: navColor }}>
                <i className="now-ui-icons shopping_cart-simple mr-1 dark"></i>
                Cart
                <span
                  style={{ alignSelf: "center" }}
                  className="badge badge-info"
                >
                  {handleQuantityCart()}
                </span>
              </p>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink style={styles.subNavbar} href="/profile">
              <p style={{ color: navColor }}>
                <i className="now-ui-icons users_circle-08 mr-1 dark"></i>
                Profile
              </p>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              style={styles.subNavbar}
              onClick={(event) => {
                handleLogout(event);
              }}
            >
              <p style={{ color: navColor }}>Logout</p>
            </NavLink>
          </NavItem>
        </>
      );
    }
  };

  const updateProductCart = () => {
    console.log(`STATE :`, cartContext.cartState.form);
    updateCart(cartContext.cartState.form).then((response) => {
      console.log(`UPDATE CART : `, response);
    });
  };

  const handleUpdateCartButton = () => {
    if (showUpdateButton === false) {
      return "";
    } else {
      return (
        <Button onClick={() => updateProductCart()} className="btn btn-warning">
          UPDATE CART
        </Button>
      );
    }
  };

  const handleUseVoucher = (voucherName) => {
    console.log(voucherName);
    const transaction = {
      voucherName: voucherName,
    };
    setVoucherToTransaction(cartContext.cartState.form.id, transaction).then(
      (response) => {
        console.log(`SET VOUCHER : `, response);
      }
    );
  };

  const handleShowVoucher = () => {
    if (
      cartContext.cartState.form.voucher === "" ||
      cartContext.cartState.form.voucher === undefined ||
      cartContext.cartState.form.voucher === null
    ) {
      return "Your Voucher :  ";
    } else {
      return "Your Voucher : " + cartContext.cartState.form.voucher.name;
    }
  };

  const closeModalVoucher = () => {
    setModalVoucher(false);
  };

  const closeModalCommitTransaction = () => {
    setModalCommitTrasaction(false);
  };

  const handleCommitTransaction = () => {
    console.log(`ID TRANSACTION :`, cartContext.cartState.form.id);
    commitTransaction(cartContext.cartState.form.id).then((response) => {
      console.log(response);
    });
  };

  return (
    <>
      {console.log(`NAVBAR : `, cartContext.cartState)}
      {collapseOpen ? (
        <div
          id="bodyClick"
          onClick={() => {
            document.documentElement.classList.toggle("nav-open");
            setCollapseOpen(false);
          }}
        />
      ) : null}
      <Navbar
        className={"fixed-top navbar-light " + navbarColor}
        expand="lg"
        style={{ backgroundColor: "rgba(270,270,270,0.7)" }}
      >
        <Container>
          <div className="navbar-translate">
            <NavbarBrand id="navbar-brand">
              <img src={Logo} alt="Nutrismart" width={"35%"} />
            </NavbarBrand>
            <button
              className="navbar-toggler navbar-toggler"
              onClick={() => {
                document.documentElement.classList.toggle("nav-open");
                setCollapseOpen(!collapseOpen);
              }}
              aria-expanded={collapseOpen}
              type="button"
            >
              <span className="navbar-toggler-bar top-bar"></span>
              <span className="navbar-toggler-bar middle-bar"></span>
              <span className="navbar-toggler-bar bottom-bar"></span>
            </button>
          </div>
          <Collapse
            className="justify-content-end"
            isOpen={collapseOpen}
            navbar
          >
            <Nav navbar>
              <NavItem>
                <NavLink style={styles.subNavbar}>
                  <Link to="/index">
                    <p style={{ color: navColor }}>
                      <i className="now-ui-icons users_circle-08 mr-1 dark"></i>
                      Home
                    </p>
                  </Link>
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink style={styles.subNavbar}>
                  <Link to="/product">
                    <p style={{ color: navColor }}>
                      <i className="now-ui-icons education_agenda-bookmark mr-1 dark"></i>
                      Product
                    </p>
                  </Link>
                </NavLink>
              </NavItem>

              {handleNavBar()}
            </Nav>
          </Collapse>
        </Container>
      </Navbar>

      {/*MODAL CART*/}
      <Container>
        <Modal isOpen={modalCart} size="lg" centered toggle={modalCart}>
          <ModalHeader
            toggle={() => {
              closeCartModal();
            }}
            tag="strong"
          >
            <img src={Logo} width="20%" alt={"logo"} />
          </ModalHeader>
          <Label tag="strong" style={{ alignSelf: "center" }}>
            CART
          </Label>
          <ModalBody style={{ marginBottom: 20 }}>{generateCart()}</ModalBody>
          {handleUpdateCartButton()}
          <ModalFooter>
            <Container>
              <Row>
                <Col>
                  <Label style={{ fontSize: "20px" }}>
                    {handleShowVoucher()}
                  </Label>
                </Col>

                <Label
                  style={{ fontWeight: "bold", fontSize: "22px" }}
                  className="mb-4"
                >
                  Grand Total : Rp.
                  {numberFormat(cartContext.cartState.form.grandTotal)}
                </Label>
              </Row>
              <Row className="lg-2 md-4 sm-4">
                <Button
                  style={{
                    width: "-webkit-fill-available",
                    backgroundColor: "rgb(142, 224, 255)",
                    color: "black",
                    fontWeight: "bold",
                    marginBottom: "15px",
                  }}
                  onClick={() => {
                    setModalVoucher(true);
                  }}
                >
                  Show Voucher
                </Button>
                <Button
                  style={{
                    width: "-webkit-fill-available",
                    backgroundColor: "#6aff2c94",
                    color: "black",
                    fontWeight: "bold",
                  }}
                  className="btn btn-info"
                  onClick={() => {
                    setModalCommitTrasaction(true);
                  }}
                >
                  Buy Now
                </Button>
              </Row>
            </Container>
          </ModalFooter>
        </Modal>
      </Container>

      {/*MODAL Voucher*/}
      <Container>
        <Modal isOpen={modalVoucher} size="md" centered toggle={modalVoucher}>
          <ModalHeader
            toggle={() => {
              closeModalVoucher();
            }}
            tag="strong"
          >
            <img src={Logo} width="20%" alt={"logo"} />
          </ModalHeader>
          <Label tag="strong" style={{ alignSelf: "center" }}>
            VOUCHER
          </Label>
          <ModalBody style={{ marginBottom: 20 }}>
            <div
              styles={{ height: "500px", overflowY: "scroll" }}
              style={styles.wrapperDiv}
            >
              {voucher.map((item, index) => {
                return (
                  <>
                    <ListVoucher
                      key={index}
                      setVoucher={handleUseVoucher}
                      data={item}
                    />
                    <hr />
                  </>
                );
              })}
            </div>
          </ModalBody>
        </Modal>
      </Container>

      {/*MODAL Make Sure Transaction*/}
      <Container>
        <Modal
          isOpen={modalCommitTransaction}
          size="md"
          centered
          toggle={modalCommitTransaction}
        >
          <ModalHeader
            toggle={() => {
              closeModalCommitTransaction();
            }}
            tag="strong"
          >
            <img src={Logo} width="20%" alt={"logo"} />
          </ModalHeader>

          <ModalBody style={{ marginBottom: 20, alignSelf: "center" }}>
            <Label tag="strong">Are you sure to continue transaction ?</Label>
            <Row>
              <Col style={{ textAlign: "center" }}>
                <Button
                  style={{
                    fontWeight: "bold",
                    fontSize: "19px",
                    backgroundColor: "#ff4500",
                  }}
                >
                  Cancel
                </Button>
                <Button
                  style={{
                    fontWeight: "bold",
                    fontSize: "19px",
                    backgroundColor: "#000000",
                  }}
                  onClick={() => {
                    handleCommitTransaction();
                  }}
                >
                  Yes
                </Button>
              </Col>
            </Row>
          </ModalBody>
        </Modal>
      </Container>
    </>
  );
}

const styles = {
  navbar: {
    fontSize: 20,
  },
  subNavbar: {
    fontSize: 14,
  },
};

export default IndexNavbar;
