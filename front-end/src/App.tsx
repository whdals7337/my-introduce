import React from "react";
import Menu from "./components/menu/Menu";
import FooterLoader from "./container/FooterLoader";
import HeaderLoader from "./container/HeaderLoader";
import IntroduceLoader from "./container/IntroduceLoader";

function App() {
  return (
    <>
      <Menu />
      <HeaderLoader />
      <IntroduceLoader />
      <FooterLoader />
    </>
  );
}

export default App;
