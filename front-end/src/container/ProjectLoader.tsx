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
    (state: RootState) => state.member.member.data?.memberId
  );

  useEffect(() => {
    dispatch(getProjectsByMemberIdThunk(memberId ? memberId : 1));
  }, []);

  return (
    <>
      {loading && <p>로딩중....</p>}
      {error && <p>에러발생</p>}
      {data && <ProjectList projects={data} />}
    </>
  );
}

export default ProjectLoader;
