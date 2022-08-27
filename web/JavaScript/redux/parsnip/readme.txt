# webpack
browserslist  当前项目需要支持的浏览器范围
> npx browserslist       -> 查看当前支持的浏览器版本 ">0.2%"  指的是市场占有率

# react 标准组件extend component的, 可以使用 this.state   this.setState
this.setState({ liked: !this.state.liked });   <- demo, 通过变更state来刷新View

# redux
-> reducer
function counterReducer(state = 0, action) {
    if (action.type === 'INCREMENT') {
        return state + 1;
    }
    return state;
}
-> store
const store = createStore(counterReducer);
-> 订阅store的变化
store.subscribe(() => {
    console.log('current state: ', store.getState());
})
-> 发送action给store 
store.dispatch({type: 'INCREMENT'});


=> 安装redux 
npm install --save-prod redux

# react-redux -> 构建组件 state 和 redux store之间的桥梁
npm install --save-prod react-redux

=> 如何关联store 里的 state 和 react的组件的state?
-> 1.0 这个组件得是标准组件 (extend component的)
-> 2.0 写mapping的方法， 就是计算出 这个组件 需要哪些props
function mapStateToProps(state) {
  return {
    tasks: state.tasks
  }
}
-> 3.0 用connect方法绑定组件
export default connect(mapStateToProps)(App);

# 组件分成两类
-> 比如TaskList 只负责接收props来渲染  => 展示型组件
-> 比如App, 负责从redux里获取数据, 构建关联的  => 容器型组件

# Redux DevTools
要能使用需要一些配置

-> 1. 安装chrome 插件Redux DevTools
-> 2. 安装依赖
npm install --save-dev redux-devtools-extension
-> 3. 修改创建store的方法
const store = createStore(counterReducer, devToolsEnhancer());