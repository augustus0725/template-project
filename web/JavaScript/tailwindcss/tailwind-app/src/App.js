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
      <div>定义box的宽 w-32 和高 h-32, padding - p-4, border-2, mx 用来设置 左右margin </div>
      <div className="w-32 h-32 border-2 border-black"></div>
      {/* 对比一下，加上box-border */}
      {/* 对比一下，box-border 限制了元素总的大小 */}
      {/*          box-content 限制了内容的大小， 总大小要加上margin， border, padding */}
      <div className="box-border h-32 w-32 p-4 border-2 border-red-500"></div>
      <div className="box-content h-32 w-32 border-2 border-yellow-300"></div>
      <div>block 级别的flex容器-----------------------------------------------------------------------------------------------------------------------------------------</div>
      <div className="flex items-center w-64 h-32">
        <img src={logo} alt="show the logo"></img>
        <div>
          <strong>Andrew Alfred</strong>
          <span>Technical advisor</span>
        </div>
      </div>
      <div>--------------------------------------------------------------------------------------------------------------------------------------------------------</div>
      {/* 使用grid 配置容器layout */}
      <div> 使用grid 配置容器layout </div>
      <div className="w-2/3 h-fit border-2 h border-blue-500 grid gap-4 grid-cols-3 grid-rows-3">
        <p>1</p><p>2</p><p>3</p>
        <p>1</p><p>2</p><p>3</p>
        <p>1</p><p>2</p><p>3</p>
      </div>

      <span className="inline-grid grid-cols-3 gap-4 border-2 border-red-500">
        <span>01</span>
        <span>02</span>
        <span>03</span>
        <span>04</span>
        <span>05</span>
        <span>06</span>
      </span>
      <span className="inline-grid grid-cols-3 gap-4 border-2 border-red-500">
        <span>01</span>
        <span>02</span>
        <span>03</span>
        <span>04</span>
        <span>05</span>
        <span>06</span>
      </span>
      <div>contents 样式 让元素没有box特性, 好像不存在一样, 但是保留了层次的关系-----------------------------------------------------------------------------------</div>
      <div className="flex border-2 border-red-500">
        <div className="flex-1 ...">01</div>
        <div className="contents">
          <div className="flex-1 ...">02</div>
          <div className="flex-1 ...">03</div>
        </div>
        <div className="flex-1 ...">04</div>
      </div>
      <div>--------------------------------------------------------------------------------------------------------------------------------------------------------</div>
      <div className="flex gap-4 border-2 border-blue-600">
        <div className="hidden">01</div>
        <div>02</div>
        <div>03</div>
      </div>
      <div className="flex gap-4 border-2 border-blue-600">
        <div className="invisible">01</div>
        <div>02</div>
        <div>03</div>
      </div>
      <div>float-left float-right float-none----------------------------------------------------------------------------------------------------------------------</div>
      <div>类似有 clean-left clean-right clean-both clean-none ------------------------------------------------------------------------------------------------</div>
      <div>
        <img src={logo} alt="show logo" className="float-right w-24 h-24"></img>
        <p>Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.</p>
      </div>
      <div>------------------------------------ 图像填充已有空间的方式 ----------------------------------------------------------------------------------------------</div>
      <div className="bg-indigo-300">
        <img src={logo} className="object-fill h-48 w-96" alt="show logo"></img>
      </div>
      <div>------------------------------------ 图像填充已有空间时候的位置设定 ----------------------------------------------------------------------------------------------</div>
      <div>------------------------------------ Overflow 当内容对于容器来说太多应该怎么展示 -----------------------------------------------------------------------------</div>
      {/* <div> <strong>overflow-visible:</strong> 就算容器空间不够也显示出来 </div>
      <div class="overflow-visible w-1/3 h-4 border-2 border-blue-500">
        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
      </div> */}
      <div> <strong>overflow-hidden:</strong> 容器空间不够, 内容会影藏 </div>
      <div className="overflow-hidden w-1/3 h-16 border-2 border-blue-500">
        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
      </div>

      <div> <strong>overflow-auto:</strong> 容器空间不够, 自动产生滚动条 </div>
      <div className="overflow-auto w-1/3 h-16 border-2 border-blue-500">
        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
      </div>

      <div> <strong>overflow-clip:</strong> 容器空间不够, 自动影藏，并且阻止了滚动条的滚动 </div>
      <div className="overflow-clip w-1/3 h-16 border-2 border-blue-500">
        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
      </div>

      <div>------------------------------------ Position 样式-----------------------------------------------------------------------------</div>
      <div>static 让block不流动， absoulute 浏览器的绝对位置-----------------------------------------------</div>
      <div className="static h-14 border-black border-2">
        <p>Static parent</p>
        <div className="absolute bottom-0 left-0 w-fit h-9 border-black border-2">
          <p>Absolute child</p>
        </div>
      </div>

      <div>------------------------------------ 元素摆放的位置 -----------------------------------------------------------------------------</div>
      <div>------------------------------------ top-0, right-0, bottom-0, left-0 -----------------------------------------------------------------------------</div>

      <div>------------------------------------ z-index -----------------------------------------------------------------------------</div>
      <div>------------------------------------ example: 图像和前面的元素重叠了， 注意absolute的使用 ---------------------------------------------------</div>
      <div className="z-40 h-14 w-1/3 border-2 border-red-500 absolute">05</div>
      <img src={logo} alt="show logo" className="-z-10 border-2 border-red-500 h-20" />

      <div>------------------------------------ 学习 flex -----------------------------------------------------------------------------</div>
      <div>------------------------------------ flex row -----------------------------------------------------------------------------</div>
      <div className="flex flex-row gap-2">
        <div className="basis-1/4 border-2 border-blue-500">01</div>
        <div className="basis-1/4 border-2 border-blue-500">02</div>
        <div className="basis-1/2 border-2 border-blue-500">03</div>
      </div>
      <div>------------------------------------ flex row reverse -----------------------------------------------------------------------------</div>
      <div className="flex flex-row-reverse gap-2">
        <div className="basis-1/4 border-2 border-blue-500">01</div>
        <div className="basis-1/4 border-2 border-blue-500">02</div>
        <div className="basis-1/2 border-2 border-blue-500">03</div>
      </div>
      <div>------------------------------------ flex col 竖直方向 -----------------------------------------------------------------------------</div>
      <div className="flex flex-col gap-2 w-1/3">
        <div className="basis-1/4 border-2 border-blue-500">01</div>
        <div className="basis-1/4 border-2 border-blue-500">02</div>
        <div className="basis-1/2 border-2 border-blue-500">03</div>
      </div>
      <div>------------------------------------ flex col reverse 竖直方向 反转  -----------------------------------------------------------------------------</div>
      <div className="flex flex-col-reverse gap-2 w-1/3">
        <div className="basis-1/4 border-2 border-blue-500">01</div>
        <div className="basis-1/4 border-2 border-blue-500">02</div>
        <div className="basis-1/2 border-2 border-blue-500">03</div>
      </div>

      <div>------------------------------------ flex nowrap items 不会换行  -----------------------------------------------------------------------------</div>
      {/* <div class="flex flex-nowrap gap-2 h-8 border-2 border-yellow-600">
        <div>01</div>
        <div>02</div>
        <div>        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
        </div>
      </div> */}

      <div>------------------------------------ flex wrap items 会换行  -----------------------------------------------------------------------------</div>
      <div className="flex flex-wrap gap-2 h-8 border-2 border-yellow-600">
        <div className="border-2 border-blue-500">01</div>
        <div className="border-2 border-blue-500">02</div>
        <div className="border-2 border-blue-500">        Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.
        </div>
      </div>

      <div>------------------------------------ flex items grow 会让元素自己占满空间， 忽略初始的长度, grow-0 不会grow, 类似的还有 shrink/shrink-0 -----------------------------------------------------------------------------</div>
      <div className="flex gap-2">
        <div className="flex-none w-14 h-14 border-2 border-blue-500">
          01
        </div>
        <div className="grow h-14 border-2 border-blue-500 w-1/3">
          02
        </div>
        <div className="flex-none w-14 h-14 border-2 border-blue-500">
          03
        </div>
      </div>

      <div>------------------------------------ flex items orders -----------------------------------------------------------------------------</div>
      <div className="flex justify-between">
        <div className="order-last border-2 border-blue-500">01</div>
        <div className="order-last border-2 border-blue-500">02</div>
        <div className="order-last border-2 border-blue-500">03</div>
      </div>

      <div className="flex justify-start gap-2">
        <div className="border-2 border-blue-500">01</div>
        <div className="border-2 border-blue-500">02</div>
        <div className="border-2 border-blue-500">03</div>
      </div>

      <div className="flex justify-center gap-2">
        <div className="border-2 border-blue-500">01</div>
        <div className="border-2 border-blue-500">02</div>
        <div className="border-2 border-blue-500">03</div>
      </div>

      <div className="flex justify-end gap-2">
        <div className="border-2 border-blue-500">01</div>
        <div className="border-2 border-blue-500">02</div>
        <div className="border-2 border-blue-500">03</div>
      </div>

      {/* 设置内容位置 */}
      <div>设置内容位置 align left</div>
      <div className="h-56 grid grid-cols-3 gap-4 content-start border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
        <div className="border-2 border-blue-500 h-8 w-16">05</div>
      </div>
      <div>设置内容位置 align center</div>
      <div className="h-56 grid grid-cols-3 gap-4 content-center border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
        <div className="border-2 border-blue-500 h-8 w-16">05</div>
        <div className="border-2 border-blue-500 h-8 w-16">06</div>
      </div>

      <div>设置内容位置  center</div>
      <div className="grid grid-cols-2 gap-4 place-content-center h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  start</div>
      <div className="grid grid-cols-2 gap-4 place-content-start h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  end</div>
      <div className="grid grid-cols-2 gap-4 place-content-end h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  between</div>
      <div className="grid grid-cols-2 gap-4 place-content-between h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  space around</div>
      <div className="grid grid-cols-2 gap-4 place-content-around h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  space evenly</div>
      <div className="grid grid-cols-2 gap-4 place-content-evenly h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500 h-8 w-16">01</div>
        <div className="border-2 border-blue-500 h-8 w-16">02</div>
        <div className="border-2 border-blue-500 h-8 w-16">03</div>
        <div className="border-2 border-blue-500 h-8 w-16">04</div>
      </div>

      <div>设置内容位置  space stretch</div>
      <div className="grid grid-cols-2 gap-4 place-content-stretch h-48 border-2 border-blue-500">
        <div className="border-2 border-blue-500">01</div>
        <div className="border-2 border-blue-500">02</div>
        <div className="border-2 border-blue-500">03</div>
        <div className="border-2 border-blue-500">04</div>
      </div>
      <br />
      {/* 控制box 的 space  */}
      {/* <div> 控制padding, p{t|r|b|l}-{size} </div> */}
      <div> 控制margin m/t/r/b/l/-size</div>
      <div className="border-2 border-blue-500 w-1/3 mx-40 p-8">控制padding p/pt/pt/pb/pl-size</div>

      <div className="border-2 border-blue-500 w-1/3"> 控制元素的宽 </div>
      <div className="border-2 border-blue-500 w-fit mx-9"> 宽度适应内容 </div>
      <div className="border-2 border-blue-500 w-max mx-9"> 最大内容宽度 </div>
      <div className="border-2 border-blue-500 w-min mx-9"> 最小内容宽度 </div>
      <div className="border-2 border-blue-500 w-full ml-9"> 最宽 </div>

      {/*  文字内容的排版  */}
      <div className="border-2 border-blue-500 w-1/3">文字内容的排版</div>
      <div className="border-2 border-blue-500 w-1/3 text-center">文字内容的排版</div>
      <div className="border-2 border-blue-500 w-1/3 text-center text-yellow-500">文字内容的排版</div>
      <div className="border-2 border-blue-500 w-1/3 text-end">文字内容的排版</div>
      <div className="border-2 border-blue-500 w-1/3 text-center decoration-red-500 underline decoration-wavy underline-offset-8">加波浪线， 并且设置offset， offset控制距离文字的距离</div>
      <div className="border-2 border-blue-500 w-1/3 text-end line-through">文字内容的排版</div>
      <div className="border-2 border-blue-500 w-1/3 text-center decoration-red-500 overline decoration-2">装饰的下划线之类的可以加颜色， 也可以控制粗细</div>

      <p className="capitalize">The quick brown fox， 每个单词首字母大写</p>
      <p>文字太多超过显示区域, 自动加...</p>
      <p className="truncate border-2 border-blue-500 w-1/3"> Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.</p>
      <p className="text-ellipsis border-2 border-blue-500 w-1/3"> Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.</p>
      <br />
      <p className="text-clip overflow-hidden border-2 border-blue-500 w-1/3 h-8"> Maybe we can live without libraries, people like you and me. Maybe. Sure, we're too old to change the world, but what about that kid, sitting down, opening a book, right now, in a branch at the local library and finding drawings of pee-pees and wee-wees on the Cat in the Hat and the Five Chinese Brothers? Doesn't HE deserve better? Look. If you think this is about overdue fines and missing books, you'd better think again. This is about that kid's right to read a book without getting his mind warped! Or: maybe that turns you on, Seinfeld; maybe that's how y'get your kicks. You and your good-time buddies.</p>

      <div>文字缩进</div>
      <p className="indent-8 border-2 border-blue-500 w-1/3">
        So I started to walk into the water. I won't lie to you boys, I was
        terrified. But I pressed on, and as I made my way past the breakers
        a strange calm came over me. I don't know if it was divine intervention
        or the kinship of all living things but I tell you Jerry at that moment,
        I <em>was</em> a marine biologist.
      </p>

      <div className="flex gap-4">
        <p>控制鼠标图案</p>
        <button type="button" className="cursor-pointer border-2 border-blue-700">
          Submit
        </button>
        <button type="button" className="cursor-progress border-2 border-blue-700">
          Saving...
        </button>
        <button type="button" disabled className="cursor-not-allowed border-2 border-blue-700">
          Confirm
        </button>
      </div>

      {/*  div 形状  */}
      <div className="flex justify-center place-items-center  w-16 h-16 rounded-full bg-red-500"> 
      <div className="w-fit h-fit">爱心</div>
      
      </div>



    </>
  );
}

export default App;
