import React from "react";
import { Skill } from "../../api/skill";
import Title from "../title/Ttitle";
import SkillItem from "./SkillItem";
import "./Skill.css";

type SkillListProps = {
  skills: Skill[];
};

function SkillList({ skills }: SkillListProps) {
  // front-end
  const frontEndList = skills.filter(function (skill) {
    return skill.level === 1;
  });

  // back-end
  const backEndList = skills.filter(function (skill) {
    return skill.level === 2;
  });

  //etc
  const etcList = skills.filter(function (skill) {
    return skill.level === 3;
  });

  return (
    <div id="skill_wrap">
      <Title title="Skills" />
      {frontEndList && frontEndList.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">FrontEnd</div>
          <ul>
            {frontEndList.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}

      {backEndList && backEndList.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">BackEnd</div>
          <ul>
            {backEndList.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}

      {etcList && etcList.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">Etc</div>
          <ul>
            {etcList.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default SkillList;
