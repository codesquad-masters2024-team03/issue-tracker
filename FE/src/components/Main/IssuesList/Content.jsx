import styled from "styled-components";
import { ContentButtonStyles } from "../../../styles/commonStyles";
import { Label } from "../../../icons/label";
import { Milestones } from "../../../icons/milestones";
import { FilterBar } from "./FilterBar";
import { IssueTable } from "./IssueTable";

export function Content() {
  return (
    <>
      <Bar>
        <FilterBar />
        <Nav>
          <Tabs>
            <button className="label">
              <Label />
              label
            </button>
            <button className="milestones">
              <Milestones />
              milestones
            </button>
          </Tabs>
          <Issue>+ 이슈 작성</Issue>
        </Nav>
      </Bar>
      <IssueTable />
    </>
  );
}

const Bar = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 20px 100px;
  height: 40px;
`;

const Nav = styled.div`
  display: flex;
  justify-content: space-between;
  width: 500px;
`;

const Tabs = styled.div`
  display: flex;
  border: solid#dadbef;
  border-radius: 10px;
  width: 320px;
  justify-content: center;
  .label,
  .milestones {
    ${ContentButtonStyles}
  }
  .label {
    border-right: solid #dadbef;
  }
`;

const Issue = styled.button`
background-color: #007bff;
border: none;
color: white;
border-radius: 10px;
cursor: pointer;
font-size: 18px;
width: 120px;
`;
