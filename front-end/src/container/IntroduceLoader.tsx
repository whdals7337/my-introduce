import React from "react";
import { RootState } from "../modules";
import { useSelector } from "react-redux";
import Introduce from "../components/introduce/Introduce";

function IntroduceLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.member.member
  );

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && (
        <Introduce
          subIntroduction={data.subIntroduction}
          introduction={data.introduction}
        />
      )}
    </>
  );
}

export default IntroduceLoader;
