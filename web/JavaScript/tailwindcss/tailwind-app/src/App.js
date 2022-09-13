import logo from "./logo.svg"

function App() {
  return (
    <>
      {/* // mx-<n>, 1 代表 4px, 设置左右margin */}
      <div className="container mx-4">
        Test for container.
      </div>
      {/* 用column 数量来限定一行几列 */}
      <div>
      用column 数量来限定一行几列
      </div>
      <div className="columns-3">
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
      </div>
      {/* 基于一个元素的宽来摆放元素 3xs, 2xs, xs, sm, md...*/}
      <div>基于一个元素的宽来摆放元素 3xs, 2xs, xs, sm, md...</div>
      <div className="columns-3xs">
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
      </div>
      {/* 元素之间的间距 */}
      <div>元素之间的间距</div>
      <div className="gap-8 columns-4">
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
      </div>
    </>
  );
}

export default App;
