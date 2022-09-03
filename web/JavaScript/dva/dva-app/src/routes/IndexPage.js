import React from 'react';
import { connect } from 'dva';
import styles from './IndexPage.css';

function IndexPage({dispatch, data}) {
  return (
    <div className={styles.normal}>
      <h1 className={styles.title}>Yay! Welcome to dva! Data is {data}</h1>
      <div className={styles.welcome} />
      <ul className={styles.list}>
        <li>To get started, edit <code>src/index.js</code> and save to reload.</li>
        <li><a href="https://github.com/dvajs/dva-docs/blob/master/v1/en-us/getting-started.md">Getting Started</a></li>
      </ul>
    </div>
  );
}

IndexPage.propTypes = {
};

const mapStateToProps = (state) => {
  console.log('state: ', state);
  return {
    // 能看到之前namespace的作用, 对应了state里的key, 不同的组件可以不会互相干扰
    data: state.example.data,
  };
}

export default connect(mapStateToProps)(IndexPage);
