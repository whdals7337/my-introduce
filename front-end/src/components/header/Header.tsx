import React from "react";

type HeaderProps = {
  comment: string;
  filePath: string;
  fileOriginName: string;
};
function Header({ comment, filePath, fileOriginName }: HeaderProps) {
  return (
    <>
      <div>
        <div>
          {filePath} {fileOriginName}
          <h3>{comment}</h3>
        </div>
      </div>
    </>
  );
}

export default Header;
