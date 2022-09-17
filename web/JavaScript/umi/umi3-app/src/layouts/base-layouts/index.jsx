import Nav from '@/components/nav';

const BaseLayouts = (props) => {
  return (
    <>
      <Nav></Nav>
      <div>It's base layout.</div>
      { props.children }
    </>
  );
};

export default BaseLayouts;
