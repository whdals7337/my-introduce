import React from "react";
import { Project } from "../../api/project";
import ProjectItem from "./ProjectItem";
import Title from "../title/Ttitle";

type ProjectListProps = {
  projects: Project[];
};

function ProjectList({ projects }: ProjectListProps) {
  return (
    <>
      <Title title="Project" />
      <ul>
        {projects.map((project) => (
          <ProjectItem project={project} key={project.projectId} />
        ))}
      </ul>
    </>
  );
}

export default ProjectList;
