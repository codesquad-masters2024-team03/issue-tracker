import styled from "styled-components";

export const Popup = ({ id, isopen, popupItems, onChange, ...props }) => (
  <StyledPopup $isopen={isopen} {...props}>
    <Ul>
      <div>{id} 필터</div>
      {popupItems.map(({ id, label }) => (
        <Li key={id}>
          <label htmlFor={id}>{label}</label>
          <Checkbox type="checkbox" id={id} onChange={onChange} />
        </Li>
      ))}
    </Ul>
  </StyledPopup>
);

const StyledPopup = styled.div`
  width: 240px;
  display: none;
  position: absolute;
  background: white;
  border-radius: 10px;
  border: solid #dadbef;
  margin: 45px 0px 0px -1px;
  div {
    color: #777777;
    padding: 0.5rem;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    background-color: #f7f7fc;
  }
  ${(props) => props.$isopen && `display: block;`}
`;

const Ul = styled.ul`
  list-style-type: none;
  padding: 0;
  margin: 0;
`;

const Li = styled.li`
  display: flex;
  justify-content: space-between;
  padding: 0.5rem;
  border-top: solid #dadbef;
`;

const Checkbox = styled.input`
  margin-right: 0.5rem;
`;

export default Popup;
