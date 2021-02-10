import React from "react";

// reactstrap components
import { Container } from "reactstrap";

// core components
function ProfilePageHeader(props) {
  let pageHeader = React.createRef();
  const { dataProfile } = props;
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
      <div
        className="page-header clear-filter page-header-small"
        filter-color="blue"
      >
        <div
          className="page-header-image"
          style={{
            backgroundImage: "url(" + require("assets/img/bg5.jpg") + ")",
          }}
          ref={pageHeader}
        ></div>
        <Container>
          <div
            className="photo-container"
            style={{ backgroundColor: "#123242" }}
          >
            <p style={{ fontSize: 70 }}>
              {dataProfile.profile.firstName.substring(0, 1) +
                dataProfile.profile.lastName.substring(0, 1)}
            </p>
          </div>
          <h3 className="title">
            {dataProfile.profile.firstName + " " + dataProfile.profile.lastName}
          </h3>
          <div className="content">
            <div className="social-description">
              <h2>48</h2>
              <p>Total Transaction</p>
            </div>
          </div>
        </Container>
      </div>
    </>
  );
}

export default ProfilePageHeader;
