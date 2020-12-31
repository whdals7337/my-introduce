import React from "react";
import Title from "../title/Ttitle";
import "./Footer.css";

type FooterProps = {
  phoneNumber: string;
  email: string;
};

function Footer({ phoneNumber, email }: FooterProps) {
  return (
    <div id="footer_wrap">
      <Title title="Contact" />
      <div className="footer_content">
        <div className="footer_item">연락처: {phoneNumber}</div>
        <div className="footer_item">E-MAIL: {email}</div>
      </div>
    </div>
  );
}

export default Footer;
