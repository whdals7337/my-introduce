import React from "react";
import { Skill } from "../../api/skill";
import Title from "../title/Ttitle";
import SkillItem from "./SkillItem";
import "./Skill.css";

type SkillListProps = {
  skills: Skill[];
};

function SkillList({ skills }: SkillListProps) {
  // Language
  const languageSkills = skills.filter(function (skill) {
    return skill.level === 1;
  });

  // Web
  const webskills = skills.filter(function (skill) {
    return skill.level === 2;
  });

  //FrameWork
  const frameWorkskills = skills.filter(function (skill) {
    return skill.level === 3;
  });

  //Database
  const databaseskills = skills.filter(function (skill) {
    return skill.level === 4;
  });

  return (
    <div id="skill_wrap">
      <Title title="Skill" />
      {languageSkills && languageSkills.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">Language</div>
          <ul>
            {languageSkills.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}

      {webskills && webskills.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">Web</div>
          <ul>
            {webskills.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}

      {frameWorkskills && frameWorkskills.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">FrameWork</div>
          <ul>
            {frameWorkskills.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}

      {databaseskills && databaseskills.length > 0 && (
        <div className="skill_list">
          <div className="skill_category_title">Database</div>
          <ul>
            {databaseskills.map((skill) => (
              <SkillItem skill={skill} key={skill.skill_id} />
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default SkillList;
