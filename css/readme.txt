# flex layout

display: flex; // webkit浏览器 -> -webkit-flex;
         inline-flex // 也可以设置行内flex
display还有一些值, 比如none, inline(让有段落效果, 换行的效果消失)

! flex的属性
<main axis> 横轴 <cross axis> 竖轴

! 方向
flex-direction: row | row-reverse | column | column-reverse

! 控制一行满了应该怎么摆放
flex-wrap
- nowrap       -> 不换行
- wrap         -> 换行, 第一排在上面
- wrap-reverse -> 换行, 第一排在下面

! flex-flow (flex-direction + flex-wrap)

! 主(横)轴上的对齐方式
justify-content
- flex-start     靠左排序
- flex-end       靠右排
- center         居中排
- space-between  item之间用空白分开, 间距相等
- space-around   每个<item+空白>相等

! 纵轴上的对齐方式
align-items
- flex-start
- flex-end
- center
- baseline item里第一行文件作为基线来对齐
- stretch  default, 占满整个空间

! 多条主轴线的对齐方式
align-content
- flex-start
- flex-end
- center
- baseline item里第一行文件作为基线来对齐
- stretch  default, 占满整个空间

! 项目级别的属性
order
- <数字, 越小越靠前>

flex-grow
- <数值, 0代表存在剩余空间, item也不会放大, 2,1,1  按照比值分空间>

flex-shrink
- 数值, 空间不足时项目怎么显示 1 代表缩小, 0代表不缩小

flex-basis
- 定义main axis大小, auto则和项目的width/height一样

flex
- flex-grow, flex-shrink, flex-basis

align-self 可以覆盖 align-items
- auto
- flex-start
- flex-end
- center
- baseline
- stretch



# box 模型
margin: 上 右 下 左



# position

position: relative;
position: fixed;
position: absolute;
position: sticky; ->  相对视窗保留位置, top:0 之后保留在 top:0的位置