import React from "react";
import { HashLink as Link } from "react-router-hash-link";
import "./Menu.css";

type MenuProps = {
  MenuOnClike: (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  LinkOnClike: () => void;
};

function Menu({ MenuOnClike, LinkOnClike }: MenuProps) {
  return (
    <div className="menu_container">
      <div className="menu__list">
        <ul>
          <li>
            <Link smooth to="#header_container">
              Top
            </Link>
          </li>
          <li>
            <Link smooth to="#introduce_wrap">
              Introduce
            </Link>
          </li>
          <li>
            <Link smooth to="#skill_wrap">
              Skill
            </Link>
          </li>
          <li>
            <Link smooth to="#project_wrap">
              Project
            </Link>
          </li>
          <li>
            <Link smooth to="#footer_wrap">
              Contact
            </Link>
          </li>
        </ul>
      </div>
      <div className="menu_bar_grid">
        <div id="menu_bar" className="open" onClick={MenuOnClike}>
          <div className="bar_01"></div>
          <div className="bar_02"></div>
          <div className="bar_03"></div>
        </div>
        <ul>
          <li onClick={LinkOnClike}>
            <Link smooth to="#header_container">
              Top
            </Link>
          </li>
          <li onClick={LinkOnClike}>
            <Link smooth to="#introduce_wrap">
              Introduce
            </Link>
          </li>
          <li onClick={LinkOnClike}>
            <Link smooth to="#skill_wrap">
              Skill
            </Link>
          </li>
          <li onClick={LinkOnClike}>
            <Link smooth to="#project_wrap">
              Project
            </Link>
          </li>
          <li onClick={LinkOnClike}>
            <Link smooth to="#footer_wrap">
              Contact
            </Link>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default Menu;
