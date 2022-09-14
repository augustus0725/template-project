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
      {/* 当hove的时候展示3列 */}
      <div>当hover的时候展示3列</div>
      <div className="columns-4 hover:columns-3">
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
      </div>
      {/* 设备为指定的设备的时候, 显示不同的样式 */}
      <div>设备为指定的设备的时候, 显示不同的样式</div>
      <div className="columns-3 md:columns-2">
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
        <img src={logo} alt="logo"></img>
      </div>
      {/* break-after-x */}
      <div>reak-after-x</div>
      <div className="columns-2">
        <p>Well, let me tell you something, ...</p>
        <p >Sure, go ahead, laugh...</p>
        <p>Maybe we can live without...</p>
        <p>Look. If you think this is...</p>
      </div>
    </>
  );
}

export default App;
