import React from "react";
import Title from "../title/Ttitle";

type FooterProps = {
  phoneNumber: string;
  email: string;
};

function Footer({ phoneNumber, email }: FooterProps) {
  return (
    <>
      <Title title="Contact me" />
      <div>
        <div>연락처: {phoneNumber}</div>
        <div>E-MAIL: {email}</div>
      </div>
    </>
  );
}

export default Footer;
