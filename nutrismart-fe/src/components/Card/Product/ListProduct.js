import React, { useState } from "react";
import {
  Button,
  Col,
  Modal,
  ModalHeader,
  ModalBody,
  Row,
  Label,
} from "reactstrap";
import LOGO from "../../../assets/img/logo/logo-fix.png";
import { numberFormat } from "shared/currency/Currency";

export const ListProduct = (props) => {
  const baseUrl = "http://10.10.11.15:5003/product/image/";
  const { product, handleAddToCart } = props;
  const detailProduct = product.productDetail.nutritionFact;
  const [modalDetail, setModalDetail] = useState(false);
  const openModalDetail = (event) => {
    event.preventDefault();
    setModalDetail(true);
  };

  const closeModal = () => {
    setModalDetail(false);
  };

  return (
    <>
      <Col md="4">
        <div className="team-player img-raised" style={{ padding: 40 }}>
          <div
            onClick={(event) => {
              openModalDetail(event);
            }}
          >
            <img
              alt="..."
              width="160"
              height="150"
              className="img-raised"
              src={baseUrl + product.id}
            ></img>
            <h4>{product.name.substring(0, 15)}</h4>
            <p className="category text-info">
              {product.category.categoryName}
            </p>
            <p className="description">{detailProduct.substring(0, 20)}</p>
            <p>Rp. {numberFormat(product.price) + " / "}</p>
            <p>{product.minBundle}</p>
          </div>
          <Button
            color="info"
            onClick={() => {
              handleAddToCart(product);
            }}
          >
            Add To Cart
          </Button>
        </div>
      </Col>
      <Modal isOpen={modalDetail} size="lg" centered toggle={modalDetail}>
        <ModalHeader
          alignSelf="center"
          toggle={() => {
            closeModal();
          }}
          tag="strong"
        >
          <img width="25%" src={LOGO} alt="Nutrismart" />
        </ModalHeader>
        <ModalBody style={{ marginBottom: 20 }}>
          <Row>
            <Col>
              <img src={baseUrl + product.id} alt="Product Photo" />
            </Col>
            <Col>
              <h3
                style={{
                  backgroundColor: "#1212",
                  textAlign: "center",
                  fontWeight: "bold",
                }}
              >
                {product.name}
              </h3>
              <Row>
                <Col
                  width="100px !important"
                  style={{ backgroundColor: "#1212" }}
                >
                  <Label>CATEGORY : </Label>
                </Col>
                <Col>
                  <Label>{product.category.categoryName}</Label>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Label>DETAIL : </Label>
                </Col>
                <Col>
                  <Label>{detailProduct}</Label>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Label>PRICE : </Label>
                </Col>
                <Col>
                  <Label>
                    {"Rp." +
                      numberFormat(product.price) +
                      " " +
                      product.minBundle}
                  </Label>
                </Col>
              </Row>
              <Button
                className="btn btn-primary"
                onClick={() => {
                  handleAddToCart(product);
                }}
              >
                Add to Cart
              </Button>
            </Col>
          </Row>
        </ModalBody>
      </Modal>
    </>
  );
};
