import './App.css';
import { useState, useEffect, useContext, useReducer, useMemo } from 'react';
import { ThemeContext } from './context';
import Child from './Child';

const initialState = {count: 0};

function reducer(state, action) {
  switch(action.type) {
    case 'increment':
      return {count: state.count + 1};
    case 'decrement':
      return {count: state.count - 1};
    default:
      throw new Error();
  }
}


function App() {
  const [count, setCount] = useState(0);  
  const theme = useContext(ThemeContext);
  // reducer (redux way)
  const [state, dispatch] = useReducer(reducer, initialState);
  // 新的内部状态 num
  const [num, setNum] = useState(1000);

  function getNum() {
    console.log("getNum");
    return num + 1;
  }
  // -> 变化 count 的时候也会触发 getNum 计算, 如果getNum 是效率比较低的函数，那是不是就有效率问题?
  // 解决方式是使用hook -> useMemo  限定哪些状态变化触发自己重新计算
  const getNumWithMemo = useMemo(() => {
    console.log("getNumWithMemo");
    return num + 1;
  }, [num]);

  // 现版本react， 无论是否修改到影响子组件的状态元素, 子组件都会渲染
  // 应该限制一下, 比如说我只用到了 count， 那应该只有 count 变了， 才引起child渲染
  // 方法： 使用高阶组件 memo， 可以看下Child 组件里的注释 和 用法

  useEffect(() => {
    // 修改 DOM
    // 字符串里的变量采用ES6语法， 字符串模板
    // 完成对 DOM 的更改后运行你的“副作用”函数
    document.title = `You have clicked ${count} times`;
  });

  return (
    <div className="App">
      <div> Learn react hooks </div>
      <div> --------------------------- </div>
      <div style={{ background: theme.background, color: theme.foreground }}> {count} </div>
      <div style={{ background: theme.background, color: theme.foreground }}> redux way count: {state.count} </div>
      <div> --------------------------- </div>
      <div> num is { getNum() } </div>
      <div> num with memo is { getNumWithMemo } </div>
      <div> --------------------------- </div>
      <Child count={ count }></Child>
      <div> --------------------------- </div>
      <button onClick={() => setCount(count + 1)}> Click me! </button>
      <button onClick={() => dispatch({type: 'increment'})}> Click me with reducer! </button>
      <button onClick={() => setNum(num + 1)}> Change Num </button>
    </div>
  );
}

export default App;
