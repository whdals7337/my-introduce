import React from "react";
import { Project } from "../../api/project";
import ProjectItem from "./ProjectItem";
import Title from "../title/Ttitle";
import "./Project.css";

type ProjectListProps = {
  projects: Project[];
};

function ProjectList({ projects }: ProjectListProps) {
  return (
    <div id="project_wrap">
      <Title title="Project" />
      <div className="project_list">
        <ul>
          {projects &&
            projects.length > 0 &&
            projects.map((project) => (
              <ProjectItem project={project} key={project.project_id} />
            ))}
        </ul>
      </div>
    </div>
  );
}

export default ProjectList;
