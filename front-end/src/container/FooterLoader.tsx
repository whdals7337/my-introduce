import React from "react";
import { RootState } from "../modules";
import { useSelector } from "react-redux";
import Footer from "../components/footer/Footer";

function FooterLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.member.member
  );

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && data.data && (
        <Footer phoneNumber={data.data.phone_number} email={data.data.email} />
      )}
    </>
  );
}

export default FooterLoader;
