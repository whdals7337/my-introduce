import React from "react";
import { Skill } from "../../api/skill";

type SkillItemProps = {
  skill: Skill;
};

function SkillItem({ skill }: SkillItemProps) {
  return (
    <li>
      <div>
        <label className="skill__hover">show</label>
      </div>
      {skill.skillId}, {skill.skillName}, {skill.level}, {skill.skillId},
      {skill.fileOriginName}, {skill.filePath}, {skill.memberId}
    </li>
  );
}

export default SkillItem;
