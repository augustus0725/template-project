import './App.css';
import { useState, useEffect, useContext } from 'react';
import { ThemeContext } from './context';

function App() {
  const [count, setCount] = useState(0);
  const theme = useContext(ThemeContext);

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
      <div> --------------------------- </div>
      <button onClick={() => setCount(count + 1)}> Click me! </button>
    </div>
  );
}

export default App;
