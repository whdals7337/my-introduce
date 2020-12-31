import React from "react";
import { Route } from "react-router-dom";
import Home from "./page/Home";
import Test from "./page/Test";

function App() {
  return (
    <div>
      <Route path="/" component={Home} exact />
      <Route path="/test" component={Test} exact />
    </div>
  );
}

export default App;
