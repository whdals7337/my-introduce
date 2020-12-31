import React from "react";
import { Project } from "../../api/project";

type ProjectItemProps = {
  project: Project;
};

function ProjectItem({ project }: ProjectItemProps) {
  return (
    <li className="project_item">
      {project.project_link && project.project_link.length > 0 && (
        <a href={project.project_link} target="blank">
          <div
            className="project_item_image"
            style={{ backgroundImage: "url(" + project.file_url + ")" }}
          >
            <div className="project_item_image_label">링크 이동</div>
          </div>
        </a>
      )}
      {!project.project_link ||
        (project.project_link.length < 1 && (
          <div
            className="project_item_image"
            style={{ backgroundImage: "url(" + project.file_url + ")" }}
          ></div>
        ))}
      <div className="project_info">
        <div className="project_title">{project.project_title}</div>
        <div className="project_content">{project.project_content}</div>
        <div className="project_post_script">{project.project_post_script}</div>
      </div>
    </li>
  );
}

export default ProjectItem;
