import React, { useEffect } from "react";
import { RootState } from "../modules";
import { useSelector, useDispatch } from "react-redux";
import { getProjectsByMemberIdThunk } from "../modules/project";
import ProjectList from "../components/project/ProjectList";

function ProjectLoader() {
  const { data, loading, error } = useSelector(
    (state: RootState) => state.project.project
  );
  const dispatch = useDispatch();

  let memberId = useSelector(
    (state: RootState) => state.member.member.data?.data.member_id
  );

  useEffect(() => {
    if (memberId) {
      dispatch(getProjectsByMemberIdThunk(memberId));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [memberId]);

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && data.data && <ProjectList projects={data.data} />}
    </>
  );
}

export default ProjectLoader;
