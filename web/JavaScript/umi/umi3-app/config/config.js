import { defineConfig } from 'umi';
import routes from "./routes"
import theme from './theme';
import proxy from './proxy';

export default defineConfig({
  // 定义编译过程, all会让依赖的包重新编译，all比较慢
  nodeModulesTransform: {
    type: 'none',
  },
  routes: routes,
  // 能保持当前页面的数据
  fastRefresh: {},
  // 配置devServer
  devServer: {
    port: 8081,
    // https: true
  },
  // 配置标题
  title: '练习UMI3',
  // 图表, /代表public目录
  favicon: '/favicon.ico',
  // 启动按需加载
  dynamicImport: {
    loading: '@/components/loading', // 按需加载过程中显示的页面
  },
  // 设置主题样式
  theme,
  // 设置反向代理
  proxy,

});
