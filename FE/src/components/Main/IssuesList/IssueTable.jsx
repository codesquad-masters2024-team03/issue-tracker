import styled from "styled-components";

export function IssueTable() {
  return (
    <StyledDiv>
      <Header></Header>
      <Issues></Issues>
    </StyledDiv>
  );
}

const StyledDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 20px 100px;
  border: solid #dadbef;
  border-radius: 10px;
`;

const Header = styled.div`
  height: 60px;
  border-bottom: solid #dadbef;
`;
const Issues = styled.div`
  height: 90px;
`;
