import { useDispatch, useSelector } from 'umi';
import { Button } from 'antd';

const Goods = () => {
  const dispatch = useDispatch();
  const { title, login } = useSelector((state) => ({
    title: state.global.title,
    login: state.global.login,
  }));

  // 尽量用hook， 不用connect来关联状态

  return <div>
    <h1> Hello, it's Goods page. Title is : {title} </h1>
    <div> Login is : {login.toString()} </div>
    <Button type="primary" onClick={() => {
      dispatch({
        type: 'global/login',
        payload: {
          username: 'admin',
          password: '123',
        }
      });
    }}>Login async</Button>
  </div>;
};

export default Goods;
