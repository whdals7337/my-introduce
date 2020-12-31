import React from "react";
import FooterLoader from "../container/FooterLoader";
import HeaderLoader from "../container/HeaderLoader";
import IntroduceLoader from "../container/IntroduceLoader";
import MenuLoader from "../container/MenuLoader";
import ProjectLoader from "../container/ProjectLoader";
import SkillLoader from "../container/SkillLoader";

function Home() {
  return (
    <div className="wraper">
      <MenuLoader />
      <HeaderLoader />
      <IntroduceLoader />
      <SkillLoader />
      <ProjectLoader />
      <FooterLoader />
    </div>
  );
}

export default Home;
