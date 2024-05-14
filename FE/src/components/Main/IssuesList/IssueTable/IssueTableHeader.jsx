import { useState } from "react";
import styled from "styled-components";
import { Dropdown } from "../../../../icons/dropdown";
import { Closed } from "../../../../icons/closed";
import { Open } from "../../../../icons/open";
import { Popup } from "../Popup";

const assigneePopupItems = [{ id: "no_assignee", label: "담당자가 없는 이슈" }];
const labelPopupItems = [{ id: "no_label", label: "레이블이 없는 이슈" }];
const milestonesPopupItems = [
  { id: "no_milestones", label: "마일스톤이 없는 이슈" },
];
const authorPopupItems = [{ id: "no_author", label: "작성자가 없는 이슈" }];

const headerFilters = [
  { id: "assignee", label: "담당자", items: assigneePopupItems },
  { id: "label", label: "레이블", items: labelPopupItems },
  { id: "milestones", label: "마일스톤", items: milestonesPopupItems },
  { id: "author", label: "작성자", items: authorPopupItems },
];

export function IssueTableHeader() {
  const [popupStates, setPopupStates] = useState({
    assignee: false,
    label: false,
    milestones: false,
    author: false,
  });

  const handleOpenContent = (id) => {
    setPopupStates((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const handleCloseContent = (id) => {
    setPopupStates((prev) => ({ ...prev, [id]: false }));
  };

  return (
    <Header>
      <Left>
        <input type="checkbox" /> {/* check 되면 이슈선택 Header로 변경 */}
        <StyledIssueBtn>
          <Open />
          <div>열린 이슈( )</div>
        </StyledIssueBtn>
        <StyledIssueBtn>
          <Closed />
          <div>닫힌 이슈( )</div>
        </StyledIssueBtn>
      </Left>
      <Right>
        {headerFilters.map((popup) => (
          <Container key={popup.id}>
            <Input
              id={popup.id}
              type="checkbox"
              onChange={() => handleOpenContent(popup.id)}
            />
            <Filter htmlFor={popup.id}>
              <div>{popup.label}</div>
              <Dropdown />
            </Filter>
            <StyledPopup
              id={popup.label}
              isopen={popupStates[popup.id]}
              popupItems={popup.items}
              onChange={() => handleCloseContent(popup.id)}
            />
          </Container>
        ))}
      </Right>
    </Header>
  );
}

const Header = styled.div`
  height: 60px;
  display: flex;
  justify-content: space-between;
`;

const Left = styled.div`
  display: flex;
  align-items: center;
  width: 300px;
  justify-content: space-between;
  margin-left: 20px;
`;

const StyledIssueBtn = styled.div`
  display: flex;
  justify-content: space-around;
  width: 110px;
`;

const Right = styled.div`
  display: flex;
  align-items: center;
  width: 450px;
  justify-content: space-between;
  margin-right: 20px;
  position: relative;
`;

const Container = styled.div`
  display: flex;
  position: relative;
`;

const StyledPopup = styled(Popup)`
  right: 0;
`;

const Input = styled.input`
  left: 0;
  visibility: hidden;
  position: absolute;
`;

const Filter = styled.label`
  display: flex;
  justify-content: space-between;
  border-radius: 10px;
  padding: 10px;
  align-items: center;
  &:hover {
    cursor: pointer;
  }
`;
