/*eslint-disable*/
import React from "react";

// reactstrap components
import { Button, Col, Container, Row, UncontrolledTooltip } from "reactstrap";
// core components

function IndexHeader() {
  let pageHeader = React.createRef();

  React.useEffect(() => {
    if (window.innerWidth > 991) {
      const updateScroll = () => {
        let windowScrollTop = window.pageYOffset / 3;
        pageHeader.current.style.transform =
          "translate3d(0," + windowScrollTop + "px,0)";
      };
      window.addEventListener("scroll", updateScroll);
      return function cleanup() {
        window.removeEventListener("scroll", updateScroll);
      };
    }
  });

  return (
    <>
      <div className="page-header clear-filter" filter-color="dark">
        <div
          className="page-header-image"
          style={{
            backgroundImage:
              "url(" + require("assets/img/background/bg11.jpg") + ")",
          }}
          ref={pageHeader}
        ></div>
        <Container>
          <div className="content-center brand" style={{ marginTop: 120 }}>
            <img
              alt="..."
              className="n-logo"
              src={require("assets/img/logo/logo-fix.png")}
            ></img>
            <h3 style={{ marginTop: 25 }}>
              Now Available On Playstore <i className="fab fa-google-play"></i>
            </h3>
          </div>
        </Container>
      </div>
    </>
  );
}

export default IndexHeader;
