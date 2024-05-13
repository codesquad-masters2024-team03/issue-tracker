import { useState } from "react";
import styled from "styled-components";
import { Dropdown } from "../../../icons/dropdown";
import { Search } from "../../../icons/search";
import { Popup } from "./Popup";

const popupItems = [
  { id: "open", label: "열린 이슈" },
  { id: "my_issues", label: "내가 작성한 이슈" },
  { id: "assigned_to_me", label: "나에게 할당된 이슈" },
  { id: "commented_by_me", label: "내가 댓글을 남긴 이슈" },
  { id: "closed", label: "닫힌 이슈" },
];

export function FilterBar() {
  const [isopen, setIsOpen] = useState(false);

  const handleToggleCheckbox = () => {
    setIsOpen(!isopen);
  };

  const handleCloseContent = () => {
    setIsOpen(false);
  };

  return (
    <Container>
      <Input id="dropdown" type="checkbox" onChange={handleToggleCheckbox} />
      <Filter htmlFor="dropdown">
        <div>필터</div>
        <Dropdown />
      </Filter>
      <Popup
        id="이슈"
        isopen={isopen}
        popupItems={popupItems}
        onChange={handleCloseContent}
      />
      <TextInput>
        <Search />
        <input type="text" defaultValue="is:issues is:open" />
      </TextInput>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  width: 560px;
  min-width: 200px;
  position: relative;
  z-index: 10;
  border: solid #dadbef;
  border-radius: 10px;
`;

const Input = styled.input`
  left: 0;
  visibility: hidden;
  position: absolute;
`;

const Filter = styled.label`
  width: 120px;
  display: flex;
  justify-content: space-between;
  border-radius: 10px;
  padding: 10px;
  align-items: center;
  &:hover {
    cursor: pointer;
  }
`;

const TextInput = styled.div`
  display: flex;
  align-items: center;
  width: 300px;
  input {
    height: 100%;
    border: none;
    background: none;
    width: -webkit-fill-available;
  }
`;
