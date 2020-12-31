import React from "react";
import "./Title.css";

type TtileProps = {
  title: string;
};
function Title({ title }: TtileProps) {
  return (
    <div className="title_wrap">
      <h3>{title}</h3>
    </div>
  );
}

export default Title;
