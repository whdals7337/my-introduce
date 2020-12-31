import React from "react";
import { Skill } from "../../api/skill";

type SkillItemProps = {
  skill: Skill;
};

function SkillItem({ skill }: SkillItemProps) {
  const labelText: string =
    skill.skill_level === 1
      ? "Beginning"
      : skill.skill_level === 2
      ? "Intermediate"
      : "Upper";

  return (
    <li>
      <div
        className="skill_image"
        style={{ backgroundImage: "url(" + skill.file_url + ")" }}
      >
        <div className="skill_image_label">{labelText} Level</div>
      </div>
      <div>
        <span>{skill.skill_name}</span>
      </div>
    </li>
  );
}

export default SkillItem;
