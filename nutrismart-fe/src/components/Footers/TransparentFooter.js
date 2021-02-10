/*eslint-disable*/
import React from "react";

// reactstrap components
import { Container } from "reactstrap";

function TransparentFooter() {
  return (
    <footer className="footer">
      <Container>
        <nav>
          <ul>
            <li>
              <a
                href="https://www.creative-tim.com?ref=nukr-transparent-footer"
                target="_blank"
              >
                Team 3 - Pascal
              </a>
            </li>
            <li>
              <a
                href="http://presentation.creative-tim.com?ref=nukr-transparent-footer"
                target="_blank"
              >
                About Us
              </a>
            </li>
          </ul>
        </nav>
      </Container>
    </footer>
  );
}

export default TransparentFooter;
