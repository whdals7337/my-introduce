import React from "react";
import { Skill } from "../../api/skill";
import Title from "../title/Ttitle";
import SkillItem from "./SkillItem";

type SkillListProps = {
  skills: Skill[];
};

function SkillList({ skills }: SkillListProps) {
  return (
    <>
      <Title title="Skill" />
      <ul>
        {skills &&
          skills.length > 0 &&
          skills.map((skill) => (
            <SkillItem skill={skill} key={skill.skillId} />
          ))}
      </ul>
    </>
  );
}

export default SkillList;
