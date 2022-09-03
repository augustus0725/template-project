# dva的目标
dva 将react, react-sage, react-router进行了轻度的封装
代码中的 action订阅 action watcher, reducer不会显得太零碎

# dva-cli 已经废弃, 已经推荐用umi脚手架来创建项目
!! dva-cli is deprecated, please use create-umi instead

# 这边只是来简单分析下dva的抽象能力,不会用这个开发项目
=> pnpm add --save-dev dva-cli
=> npx dva new dva-app -> 创建一个dva项目

# 目录说明
src/index.js 全局的main函数, 启动过程中的配置和注册 以及全局样式
---------------------------------------------------------------
import dva from 'dva';
import './index.css';

// 1. Initialize
const app = dva();

// 2. Plugins
// app.use({});

// 3. Model
// app.model(require('./models/example').default);

// 4. Router
app.router(require('./router').default);

// 5. Start
app.start('#root');
---------------------------------------------------------------
src/components: 存放公共组件

src/routes: 存放的是页面级别的组件, 其他的脚手架里可能是pages目录
src/riyter.js 配置 <路由> 和 <页面组件>的关系
   <Route path="/" exact component={IndexPage} />
   -> 懒加载路由的方式             {React.lazy(() => import('./routers/home'))}
   -> 然后整理用 <React.Suspense fallback={<div>Loading</div>}> </React.Suspense> 包裹起来

src/models: 数据的处理流程, 其实就是简化了redux, redux-sage
# model的流程
=> 1. 在src/models里创建一个model, 这里直接用src/models/example.js
      -> namespace: 'example',  --> 在store的payload名字
      -> state: {},
      -> effects: {} 就是真实的业务逻辑, 处理同步异步业务 
      -> reducers: {} redux的经典概念   
   2. 注册这个model
     app.model(require('./models/example').default);
   3. 将model和组件关联, 让组件拿到数据
