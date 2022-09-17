import { useState } from 'react';
import styles from './index.less';
import { Button } from 'antd';
import { Button as V2Button } from 'antd-mobile';
import { useHistory } from 'umi';

export default function IndexPage({history}) {
  const [count, setCount] = useState(0);
  const hookHistory = useHistory();

  const fetchData = () => {
    let result = fetch('/umi/goods').then((response) => {
      return response.json();
    }).then((data) => {
      console.log("data is :", data);
    });
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
      <Button type="primary" onClick={() => history.push('/login')}>login with history</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={() => hookHistory.push('/login', {a:3})}>login with history hooks</Button>
      {/* fetch接口调用, fetch是js自带的 */}
      <br/>
      <br/>
      <Button type="primary" onClick={fetchData}>Fetch data with fetch</Button>
    </div>
  );
}
