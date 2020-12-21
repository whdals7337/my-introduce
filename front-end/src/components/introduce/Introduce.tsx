import React from "react";
import Title from "../title/Ttitle";

type IntroduceProps = {
  subIntroduction: string;
  introduction: string;
};
function Introduce({ subIntroduction, introduction }: IntroduceProps) {
  return (
    <>
      <Title title="Introduce" />
      <div>
        <div>{subIntroduction}</div>
        <div>{introduction}</div>
      </div>
    </>
  );
}

export default Introduce;
