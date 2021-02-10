import React, { useReducer } from "react";
import ReactDOM from "react-dom";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

// styles for this kit
import "assets/css/bootstrap.min.css";
import "assets/scss/now-ui-kit.scss";
import "assets/demo/demo.css";
import "assets/demo/nucleo-icons-page-styles.css";
import Home from "views/Home";
import SignUp from "views/pages/signup/SignUp";
import Product from "views/pages/product/Product";
import Profile from "views/pages/profile/Profile";
import { CookiesProvider } from "react-cookie";
import GlobalState from "components/Context/GlobalState";

function App() {
  return (
    <BrowserRouter>
      <CookiesProvider>
        <GlobalState>
          <Switch>
            <Route path="/profile" render={(props) => <Profile {...props} />} />

            <Route path="/index" render={(props) => <Home {...props} />} />
            <Route path="/signup" render={(props) => <SignUp {...props} />} />
            <Route path="/product" render={(props) => <Product {...props} />} />
            <Redirect to="/index" />
            <Redirect from="/" to="/index" />
          </Switch>
        </GlobalState>
      </CookiesProvider>
    </BrowserRouter>
  );
}

ReactDOM.render(<App />, document.getElementById("root"));
