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
      <div>break-after-x</div>
      <div>  不加break ------------------------------------------------------------------------------------------------------------------------------------</div>
      <div className="columns-2">
        <p>Well, let me tell you something, ...</p>
        <p >Sure, go ahead, laugh Sure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laugh</p>
        <p>Maybe we can live without...</p>
        <p>Look. If you think this is...</p>
      </div>
      <div>--------------------------------------------------------------------------------------------------------------------------------------------------------</div>
      <div className="columns-2">
        <p>Well, let me tell you something, ...</p>
        <p className="break-after-column">Sure, go ahead, laugh Sure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laughSure, go ahead, laugh</p>
        <p>Maybe we can live without...</p>
        <p>Look. If you think this is...</p>
      </div>
      <div>--------------------------------------------------------------------------------------------------------------------------------------------------------</div>
      {/* 换行之后, 颜色渐变怎么处理, slice-> 分段完成渐变 or clone 每段都产生渐变效果 */}
      <div>换行之后, 颜色渐变怎么处理, slice- 分段完成渐变 or clone 每段都产生渐变效果</div>
      <div>
        <span className="box-decoration-slice bg-gradient-to-r from-indigo-600 to-pink-500 text-white px-2">
          Hello<br />
          World
        </span> <br />
        <span className="box-decoration-clone bg-gradient-to-r from-indigo-600 to-pink-500 text-white px-2">
          Hello<br />
          World
        </span>
      </div>
      {/* 定义box的宽 w-32 和高 h-32, padding - p-4, border-2 */}
      <div>定义box的宽 w-32 和高 h-32, padding - p-4, border-2 </div>
      <div className="w-32 h-32 border-2 border-black"></div>
      {/* 对比一下，加上box-border */}
      {/* 对比一下，box-border 限制了元素总的大小 */}
      {/*          box-content 限制了内容的大小， 总大小要加上margin， border, padding */}
      <div class="box-border h-32 w-32 p-4 border-2 border-red-500"></div>
      <div className="box-content h-32 w-32 border-2 border-yellow-300"></div>
      <div>-bolock 级别的flex容器-----------------------------------------------------------------------------------------------------------------------------------------</div>
      <div className="flex items-center w-64 h-32">
        <img src={logo} alt="show the logo"></img>
        <div>
          <strong>Andrew Alfred</strong>
          <span>Technical advisor</span>
        </div>
      </div>


    </>
  );
}

export default App;
