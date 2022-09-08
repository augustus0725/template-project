import './App.css';
import { useState } from 'react';

function App() {
  const [count, setCount] = useState(0);

  return (
    <div className="App">
      <div> Learn react hooks </div>
      <div> --------------------------- </div>
      <div> {count} </div>
      <div> --------------------------- </div>
      <button onClick={() => setCount(count + 1)}> Click me! </button>
    </div>
  );
}

export default App;
