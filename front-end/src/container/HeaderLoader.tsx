import React from "react";
import { RootState } from "../modules";
import { useSelector } from "react-redux";
import Header from "../components/header/Header";

function HeaderLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.member.member
  );

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && data.data && (
        <Header comment={data.data.comment} fileUrl={data.data.file_url} />
      )}
    </>
  );
}

export default HeaderLoader;
