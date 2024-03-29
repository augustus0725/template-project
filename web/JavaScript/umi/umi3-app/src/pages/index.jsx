import { useState } from 'react';
import styles from './index.less';
import { Button } from 'antd';
import { Button as V2Button } from 'antd-mobile';
import { useHistory, request, useRequest, connect } from 'umi';

const IndexPage = (/*{history, title, login, dispatch}*/props) => {
  const [count, setCount] = useState(0);
  const hookHistory = useHistory();

  const {data, error, loading} = useRequest('/mock/list');

  if(error) {
    console.log(error);
    return (
      <div>error: ......... </div>
    );
  }

  if(loading) {
    return (
      <div>Loading..........</div>
    );
  }

  if(data) {
    console.log(data);
  }

  const fetchData = () => {
    fetch('/umi/goods').then((response) => {
      return response.json();
    }).then((data) => {
      console.log("data is :", data);
    });
  };

  const loginCallback = () => {
    fetch('/umi/login', {
      method: 'post',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        username: 'admin',
        password: '123',
      }),
    }).then((response) => {
      return response.json();
    }).then((data) => {
      console.log("Login result: ", data);
    })
  };

  const fetchDataWithRequest = async () => {
    let data = await request('/umi/goods');
    console.log('data: ', data);
  };

  const loginWithRequest = async () => {
    let data = await request('/umi/login', {
      method: 'post',
      // headers 自动加了 application/json
      data: {
        username: 'sabo',
        password: '123',
      }
    });
    console.log("Login data is :", data);
  }

  return (
    <div>
      <h1 className={styles.title}>Page index</h1>
      <div className='mylink'>count is : {count}</div>
      <div className={styles.text_red}>count is : {count}</div>
      <div className={styles.a1}>count is : {count}</div>
      <Button type="primary" onClick={() => setCount(count+1)}>Add</Button>
      <V2Button type="primary" size="small" inline>V2-Mobile</V2Button>
      {/* 使用编程做跳转 */}
      <br/>
      <br/>
      <Button type="primary" onClick={() => props.history.push('/login')}>login with history</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={() => hookHistory.push('/login', {a:3})}>login with history hooks</Button>
      {/* fetch接口调用, fetch是js自带的 */}
      <br/>
      <br/>
      <Button type="primary" onClick={fetchData}>Fetch data with fetch</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={loginCallback}>Login</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={fetchDataWithRequest}>Fetch data with umi request</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={loginWithRequest}>Login with umi request</Button>
      <br/>
      <br/>
      <div> dva state.title : {props.title} </div>
      <br/>
      <br/>
      <Button type="primary" onClick={() => {
        props.dispatch({
          type: 'global/setTitle'
        });
      }}>change dva title</Button>
      <br/>
      <br/>

      {/* Boolean 变量在页面上显示要变成string, 不然显示不了 */}
      <div> Login is : {props.login.toString()} </div>
      <div className={styles.wrapper_btn}>
      <Button type="primary" onClick={() => {
        props.dispatch({
          type: 'global/login',
          payload: {
            username: 'admin',
            password: '123',
          }
        });
      }}>Login async</Button></div>
    </div>
  );
}

// 用connect关联 dva的全局状态
export default connect((state) => {
  return {
    title: state.global.title,
    login: state.global.login,
  };
})(IndexPage);
