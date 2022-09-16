import { useState } from 'react';
import styles from './index.less';

export default function IndexPage() {
  const [count, setCount] = useState(0);
  return (
    <div>
      <h1 className={styles.title}>Page index</h1>
      <div>{count}</div>
      <button onClick={() => setCount(count+1)}>+</button>
    </div>
  );
}
