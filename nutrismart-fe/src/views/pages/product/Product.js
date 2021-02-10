import React, { useReducer } from "react";

// reactstrap components
import {
  Button,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Container,
  Row,
  Col,
  Form,
  Label,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import Pagination from "react-js-pagination";
// core components
import LandingPageHeader from "components/Headers/LandingPageHeader.js";
import DefaultFooter from "components/Footers/DefaultFooter.js";
import NavbarProduct from "views/pages/product/NavbarProduct";
import { HANDLE_INPUT_CHANGE } from "reducers/Actions";
import { LoginReducer } from "reducers/LoginReducer";
import { initialState } from "reducers/LoginReducer";
import { login } from "services/loginService";
import photos from "../../../assets/img/background/mengapa-belanja.png";
import { useCookies } from "react-cookie";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import AllProduct from "../../../assets/img/category/semua-produk.png";
import Veggie from "../../../assets/img/category/sayur.png";
import Fruit from "../../../assets/img/category/buah.png";
import Other from "../../../assets/img/category/lain-lain.png";
import AnimalProduct from "../../../assets/img/category/animalproduct.png";
import Seeds from "../../../assets/img/category/seeds.png";
import Roots from "../../../assets/img/category/roots.png";
import ProcessedProduct from "../../../assets/img/category/processed-product.png";
import { productReducer } from "reducers/productReducer";
import { initialStateProduct } from "reducers/productReducer";
import { getProduct } from "./../../../services/productService";
import { HANDLE_FETCH } from "reducers/Actions";
import { ListProduct } from "components/Card/Product/ListProduct";
import { getProductByCategory } from "services/categoryService";
import ShopContext from "components/Context/ShopContext";
import { addToCart } from "services/cartService";
import { getProductByCategoryAndPage } from "services/categoryService";

function Product() {
  const [state, dispatch] = useReducer(LoginReducer, initialState);
  const [stateProduct, dispatchProduct] = useReducer(
    productReducer,
    initialStateProduct
  );
  const [modalOpen, setModalOpen] = React.useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["id"]);
  const [modalDetail, setModalDetail] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(false);
  const cartContext = React.useContext(ShopContext);
  const [activePage, setActivePage] = React.useState(1);
  const [activeCategory, setActiveCategory] = React.useState();
  const [totalPage, setTotalPage] = React.useState(0);

  React.useEffect(() => {
    document.body.classList.add("landing-page");
    document.body.classList.add("sidebar-collapse");
    document.documentElement.classList.remove("nav-open");
    setActivePage(1);
    handleLoadData(activePage);
    return function cleanup() {
      document.body.classList.remove("landing-page");
      document.body.classList.remove("sidebar-collapse");
    };
  }, []);

  const handleChangeLogin = (inputName, inputValue) => {
    dispatch({ type: HANDLE_INPUT_CHANGE, payload: { inputName, inputValue } });
  };

  const cancelSignIn = () => {
    setModalOpen(false);
  };

  const handleLogin = (event) => {
    event.preventDefault();
    login(state.form).then((response) => {
      if (response.data === "") {
        window.alert("Sorry Account Not Registered.");
      } else {
        setCookie("id", response.data.id, { maxAge: 8640 });
        setModalOpen(false);
      }
    });
  };

  const handleLoadData = (pageNumber) => {
    setIsLoading(true);
    console.log(`ACTIVE PAGE :`, activePage);
    getProduct(pageNumber).then((response) => {
      setTotalPage(response.data.totalPages - 1);
      dispatchProduct({ type: HANDLE_FETCH, payload: response.data.content });
      setIsLoading(false);
    });
  };

  const settings = {
    dots: true,
    infinite: false,
    speed: 300,
    slidesToShow: 4,
    slidesToScroll: 4,
  };

  const handleClickCategory = (inputName, event) => {
    setIsLoading(true);

    let id = "";
    switch (inputName) {
      case "All":
        window.location.href = "/product";
        break;
      case "Fruit":
        id = "ff808181725ac19101725ac69f1b0007";
        break;
      case "AnimalProduct":
        id = "ff808181725ac19101725ac67ae60006";
        break;
      case "Veggies":
        id = "ff808181725ac19101725ac6cd180008";
        break;
      case "Seeds":
        id = "ff808181725ac19101725ac64cbe0005";
        break;
      case "Roots":
        id = "ff808181725ac19101725ac62de00004";
        break;
      case "ProcessedProduct":
        id = "ff808181725ac19101725ac605110003";
        break;
      case "Other":
        id = "ff808181725ac19101725ac5ba310002";
        break;
      default:
        break;
    }
    setActiveCategory(id);
    setActivePage(1);
    getProductByCategory(id).then((response) => {
      console.log(`TOTAL PAGE :`, response.data.totalPages);
      setTotalPage(response.data.totalPages - 1);
      dispatchProduct({ type: HANDLE_FETCH, payload: response.data.content });
      setTimeout(() => {
        setIsLoading(false);
      }, 3000);
    });
  };

  const handleAddToCart = (product) => {
    // ADD CART IF YOU USING REDUCER
    // const updateCart = cartContext.cartState.form.transactionDetails;
    // const cart = updateCart.findIndex((item) => item.id === product.id);
    // const detail = {
    //   id: "",
    //   price: product.price,
    //   quantity: "1",
    //   subTotal: product.price,
    //   product: product,
    // };
    // if (cart < 0) {
    //   updateCart.push(detail);
    // } else {
    //   const updateItem = updateCart[cart];
    //   updateItem.quantity++;
    //   updateCart[cart] = updateItem;
    // }
    // console.log(`UPDATE :`, updateCart);

    const transaction = {
      accountId: cookies.id,
      productId: product.id,
      productQty: "1",
    };

    addToCart(transaction).then((response) => {
      console.log(response);
    });
  };

  const handlePageChange = (pageNumber) => {
    console.log(`active page is ${pageNumber}`);
    setActivePage(pageNumber);
    setIsLoading(true);
    console.log(`ACTIVE CATEGORY :`, activeCategory);
    if (activeCategory === undefined) {
      handleLoadData(pageNumber);
    } else {
      getProductByCategoryAndPage(activeCategory, pageNumber).then(
        (response) => {
          console.log(`TOTAL PAGE :`, response.data.totalPages);
          setTotalPage(response.data.totalPages - 1);
          dispatchProduct({
            type: HANDLE_FETCH,
            payload: response.data.content,
          });
          setTimeout(() => {
            setIsLoading(false);
          }, 3000);
        }
      );
    }
  };

  return (
    <>
      {console.log(`TOTAL PAGE RENDER : `, totalPage)}
      <NavbarProduct setModalOpen={setModalOpen} />
      <div className="wrapper">
        <LandingPageHeader />
        <div className="section section-team text-center">
          <Container>
            <div style={{ marginBottom: 30 }}>
              <img src={photos} width="100%" alt={"...."} />
            </div>
            <div
              style={{ marginTop: 70, marginBottom: 100, marginLeft: "auto" }}
            >
              <h2> Category Product </h2>
              <Slider {...settings}>
                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("All", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={AllProduct}
                    alt="All Product"
                  />
                </div>
                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("Fruit", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={Fruit}
                    alt="Fruit"
                  />
                </div>
                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("AnimalProduct", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={AnimalProduct}
                    alt="Animal Product"
                  />
                </div>

                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("Veggies", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={Veggie}
                    alt="Veggie"
                  />
                </div>

                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("Seeds", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={Seeds}
                    alt="Seeds"
                  />
                </div>

                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("Roots", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={Roots}
                    alt="Roots"
                  />
                </div>
                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("ProcessedProduct", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={ProcessedProduct}
                    alt="Processed Product"
                  />
                </div>
                <div>
                  <img
                    onClick={(event) => {
                      handleClickCategory("Other", event);
                    }}
                    style={{ display: "block", margin: "auto" }}
                    src={Other}
                    alt="Other"
                  />
                </div>
              </Slider>
            </div>
            <Container>
              <Input placeholder={"Search product . . ."} />
            </Container>

            <div className="team">
              <Row>
                {stateProduct.products.map((product, index) => {
                  return (
                    <ListProduct
                      key={index}
                      product={product}
                      handleAddToCart={handleAddToCart}
                    />
                  );
                })}
              </Row>
            </div>
            <Container>
              <Pagination
                itemClass="page-item"
                linkClass="page-link"
                activePage={activePage}
                //total page

                itemsCountPerPage={totalPage}
                //size
                totalItemsCount={12}
                //dispaypage
                pageRangeDisplayed={5}
                onChange={handlePageChange.bind(this)}
              />
            </Container>
          </Container>
        </div>
        <Container>
          <Modal isOpen={modalOpen} size="md" centered toggle={modalOpen}>
            <ModalHeader
              toggle={() => {
                cancelSignIn();
              }}
              tag="strong"
            >
              Login
            </ModalHeader>
            <ModalBody style={{ marginBottom: 20 }}>
              <Form>
                <InputGroup className={"input-group-focus"}>
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="fa fa-user-circle"></i>
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Email"
                    type="email"
                    value={state.form.username}
                    onChange={(event) => {
                      handleChangeLogin("userName", event.target.value);
                    }}
                  ></Input>
                </InputGroup>
                <InputGroup className={"input-group-focus"}>
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="fas fa-key"></i>
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Password"
                    type="password"
                    value={state.form.password}
                    onChange={(event) => {
                      handleChangeLogin("password", event.target.value);
                    }}
                  ></Input>
                </InputGroup>
              </Form>
              <Label>
                Don't have account ?
                <button
                  class="btn btn-primary btn-link"
                  style={{ color: "#009dff", fontSize: 15, fontWeight: "bold" }}
                  onClick={() => {
                    window.location.href = "/signup";
                  }}
                >
                  Sign Up
                </button>
              </Label>
            </ModalBody>
            <ModalFooter>
              <Button
                type="button"
                color="danger"
                onClick={(event) => {
                  handleLogin(event);
                }}
              >
                Confirm
              </Button>
              <Button
                type="button"
                color="secondary"
                onClick={() => cancelSignIn()}
              >
                Cancel
              </Button>
            </ModalFooter>
          </Modal>
        </Container>

        <Container>
          <Modal isOpen={isLoading} style={{ width: "fit-content" }} centered>
            <img
              src="https://i.gifer.com/7kvr.gif"
              alt="this slowpoke moves"
              width="150"
              alt="Loading"
            />
          </Modal>
        </Container>
        <DefaultFooter />
      </div>
    </>
  );
}

export default Product;
