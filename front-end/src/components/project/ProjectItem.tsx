import React from "react";
import { Project } from "../../api/project";

type ProjectItemProps = {
  project: Project;
};
function ProjectItem({ project }: ProjectItemProps) {
  return (
    <li>
      <div>
        <label className="project__hover">show</label>
      </div>
      {project.projectId}, {project.projectTitle}, {project.projectContent},
      {project.projectPostScript}, {project.filePath}, {project.level}
    </li>
  );
}

export default ProjectItem;
