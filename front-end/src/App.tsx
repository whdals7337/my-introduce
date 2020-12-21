import React from "react";
import Menu from "./components/menu/Menu";
import FooterLoader from "./container/FooterLoader";
import HeaderLoader from "./container/HeaderLoader";
import IntroduceLoader from "./container/IntroduceLoader";
import ProjectLoader from "./container/ProjectLoader";

function App() {
  return (
    <>
      <Menu />
      <HeaderLoader />
      <IntroduceLoader />
      <ProjectLoader />
      <FooterLoader />
    </>
  );
}

export default App;
