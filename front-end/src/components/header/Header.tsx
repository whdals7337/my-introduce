import React from "react";
import "./Header.css";
import Typist from "react-typist";

type HeaderProps = {
  comment: string;
  fileUrl: string;
};

function Header({ comment, fileUrl }: HeaderProps) {
  return (
    <div
      id="header_container"
      style={{
        backgroundImage: "url(" + fileUrl + ")",
      }}
    >
      <h1 className="header__comment">
        <Typist
          startDelay={300}
          avgTypingDelay={100}
          cursor={{ hideWhenDone: true, hideWhenDoneDelay: 100 }}
        >
          {comment}
        </Typist>
      </h1>
    </div>
  );
}

export default Header;
