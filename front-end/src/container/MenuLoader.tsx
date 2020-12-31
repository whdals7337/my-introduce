import React from "react";
import Menu from "../components/menu/Menu";

function MenuLoader() {
  const headerMenuOnClike = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>
  ) => {
    e.preventDefault();
    if (e.currentTarget.classList.contains("open")) {
      e.currentTarget.classList.remove("open");
      e.currentTarget.classList.add("close");
    } else if (e.currentTarget.classList.contains("close")) {
      e.currentTarget.classList.remove("close");
      e.currentTarget.classList.add("open");
    } else {
      e.currentTarget.classList.add("open");
    }
  };

  const LinkOnClike = () => {
    const menu_bar_el = document.getElementById("menu_bar");
    if (menu_bar_el?.classList.contains("close")) {
      menu_bar_el.classList.remove("close");
      menu_bar_el.classList.add("open");
    }
  };
  return (
    <Menu MenuOnClike={headerMenuOnClike} LinkOnClike={LinkOnClike}></Menu>
  );
}

export default MenuLoader;
