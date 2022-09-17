import { NavLink } from 'umi';

const Nav = () => {
  return (
    <>
      <NavLink to="/login"> login </NavLink>
      <NavLink to="/goods"> goods </NavLink>
      <NavLink to={{ pathname: '/goods/4', query: { a: 1, b: 2 } }}>
        goods-detail
      </NavLink>
    </>
  );
};

export default Nav;
