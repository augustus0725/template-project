import './App.css';
import { useState, useEffect, useContext, useReducer } from 'react';
import { ThemeContext } from './context';

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
      <button onClick={() => setCount(count + 1)}> Click me! </button>
      <button onClick={() => dispatch({type: 'increment'})}> Click me with reducer! </button>
    </div>
  );
}

export default App;
