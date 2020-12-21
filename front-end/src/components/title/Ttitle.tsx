import React from "react";

type TtileProps = {
  title: string;
};
function Title({ title }: TtileProps) {
  return (
    <div>
      <h3>{title}</h3>
    </div>
  );
}

export default Title;
