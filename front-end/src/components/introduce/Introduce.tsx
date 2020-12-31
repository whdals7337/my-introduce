import React from "react";
import Title from "../title/Ttitle";
import "./Introduce.css";

type IntroduceProps = {
  subIntroduction: string;
  introduction: string;
};
function Introduce({ subIntroduction, introduction }: IntroduceProps) {
  return (
    <div id="introduce_wrap">
      <Title title="Introduce" />
      <div className="intro">
        <div className="intro_sub_intro">"{subIntroduction}"</div>
        <div className="intro_main_intro">{introduction}</div>
      </div>
    </div>
  );
}

export default Introduce;
