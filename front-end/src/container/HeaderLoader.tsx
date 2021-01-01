import React, { useEffect } from "react";
import { RootState } from "../modules";
import { useSelector, useDispatch } from "react-redux";
import { getMemberThunk } from "../modules/member";
import Header from "../components/header/Header";

function HeaderLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.member.member
  );
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getMemberThunk());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && (
        <Header comment={data.data.comment} fileUrl={data.data.file_url} />
      )}
    </>
  );
}

export default HeaderLoader;
