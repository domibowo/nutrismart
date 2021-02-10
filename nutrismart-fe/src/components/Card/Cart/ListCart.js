import React from "react";
import { Container, Col, Row, Input } from "reactstrap";
import { numberFormat } from "shared/currency/Currency";

export const ListCart = (props) => {
  const { data } = props;
  const { quantity } = props;
  const { removeCart } = props;
  const baseUrl = "http://10.10.11.15:5003/product/image/";
  return (
    <>
      <hr />
      <Container style={{ marginBottom: 25 }}>
        <Row>
          <Col>
            <img src={baseUrl + data.product.id} alt="Product" />
          </Col>
          <Col
            style={{
              alignSelf: "center",
              textAlign: "center",
              fontWeight: "bold",
            }}
          >
            {data.product.name}
          </Col>
          <Col style={{ alignSelf: "center" }}>
            Rp. {numberFormat(data.price)}
          </Col>
          <Col
            style={{
              alignSelf: "center",
              "-webkit-text-stroke-width": "thick",
            }}
          >
            <Input
              type="number"
              onChange={(event) => {
                quantity(data.id, event.target.value);
              }}
              value={data.quantity}
            />
          </Col>
          <Col style={{ alignSelf: "center" }}>
            Rp. {numberFormat(data.subTotal)}
          </Col>
          <Col style={{ alignSelf: "center" }}>
            <i
              class="fa fa-trash"
              aria-hidden="true"
              onClick={() => {
                removeCart(data.id);
              }}
            ></i>
          </Col>
        </Row>
      </Container>
      <hr />
    </>
  );
};
