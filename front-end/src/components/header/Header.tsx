import React from "react";
import "./Header.css";

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
      <h1 className="header__comment">{comment}</h1>
    </div>
  );
}

export default Header;
