import React, { useEffect } from "react";
import FooterLoader from "../container/FooterLoader";
import HeaderLoader from "../container/HeaderLoader";
import IntroduceLoader from "../container/IntroduceLoader";
import MenuLoader from "../container/MenuLoader";
import ProjectLoader from "../container/ProjectLoader";
import SkillLoader from "../container/SkillLoader";
import { RootState } from "../modules";
import { useSelector, useDispatch } from "react-redux";
import { getMemberThunk } from "../modules/member";

function Home() {
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
      {data && !data.data && <p>선택된 멤버없음</p>}
      {data && data.data && (
        <div className="wraper">
          <MenuLoader />
          <HeaderLoader />
          <IntroduceLoader />
          <SkillLoader />
          <ProjectLoader />
          <FooterLoader />
        </div>
      )}
    </>
  );
}

export default Home;
