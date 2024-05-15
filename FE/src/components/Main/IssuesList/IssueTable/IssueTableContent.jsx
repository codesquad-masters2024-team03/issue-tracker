import styled from "styled-components";
import { Link } from "react-router-dom";
import { Open } from "../../../../icons/open";
import { issues } from "../../../../test.json"; // test data

export function IssueTableContent({ checkedItems, onIssueCheckboxChange }) {
  return (
    <>
      {issues.length === 0 ? (
        <Issues />
      ) : (
        issues
          .slice()
          .reverse()
          .map((issue, index) => (
            <Issues key={index}>
              <Checkbox
                type="checkbox"
                checked={checkedItems.has(issue.issue_id)}
                onChange={() => onIssueCheckboxChange(issue.issue_id)}
              />
              <Content>
                <Top>
                  <Open />
                  <StyledLink to={`/issues/${issue.issue_id}`}>
                    <div className="title">{issue.title}</div>
                  </StyledLink>
                  <div>{issue.labels}</div>
                </Top>
                <Bottom>
                  <div>#{issue.issue_id}</div>
                  <div>
                    이 이슈가 {issue.create_time} 전, {issue.assignee}님에 의해
                    작성되었습니다
                  </div>
                  <div>{issue.milestone}</div>
                </Bottom>
              </Content>
            </Issues>
          ))
      )}
    </>
  );
}

const Issues = styled.div`
  display: flex;
  height: 90px;
  background-color: white;
  border-top: 1px solid #dadbef;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  margin-left: 35px;
  div {
    margin-right: 20px;
  }
  .title {
    margin-left: 10px;
    font-size: 20px;
  }
`;

const Top = styled.div`
  display: flex;
  align-items: center;
  height: 30px;
`;

const Bottom = styled.div`
  display: flex;
  align-items: center;
  height: 30px;
  color: #6e7191;
`;

const Checkbox = styled.input`
  height: 50%;
  padding: 20px;
  margin-left: 25px;
`;

const StyledLink = styled(Link)`
  color: black;
  text-decoration: none;
`;
