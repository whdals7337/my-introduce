import React, { useEffect } from "react";
import { RootState } from "../modules";
import { useSelector, useDispatch } from "react-redux";
import { getSkillsByMemberIdThunk } from "../modules/skill";
import SkillList from "../components/skill/SkillList";

function SkillLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.skill.skill
  );
  const dispatch = useDispatch();

  let memberId = useSelector(
    (state: RootState) => state.member.member.data?.memberId
  );

  useEffect(() => {
    dispatch(getSkillsByMemberIdThunk(memberId ? memberId : 1));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && <SkillList skills={data} />}
    </>
  );
}

export default SkillLoader;
