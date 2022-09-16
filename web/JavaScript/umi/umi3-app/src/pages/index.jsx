import { useState } from 'react';
import styles from './index.less';
import { Button } from 'antd';
import { Button as V2Button } from 'antd-mobile';

export default function IndexPage() {
  const [count, setCount] = useState(0);
  return (
    <div>
      <h1 className={styles.title}>Page index</h1>
      <div>{count}</div>
      <Button type="primary" onClick={() => setCount(count+1)}>Add</Button>
      <V2Button type="primary" size="small" inline>V2-Mobile</V2Button>
    </div>
  );
}
