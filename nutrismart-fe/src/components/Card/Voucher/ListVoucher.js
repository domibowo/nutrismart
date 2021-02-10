import React from "react";
import { Label, Row, Col, Button } from "reactstrap";

export const ListVoucher = (props) => {
  const { data, setVoucher } = props;
  return (
    <>
      <Row>
        <Col
          style={{
            textAlign: "center",
            alignSelf: "center",
            fontSize: "19px",
            fontWeight: "bold",
          }}
        >
          <Label>{data.name}</Label>
        </Col>
        <Col style={{ whiteSpace: "nowrap", textAlign: "center" }}>
          <Button
            onClick={() => {
              setVoucher(data.name);
            }}
            style={{ backgroundColor: "rgb(142, 224, 255)" }}
          >
            Use voucher
          </Button>
        </Col>
      </Row>
    </>
  );
};
