export default {
  '/api': {
    target: 'http://localhost:9001',
    // https: true,
    changeOrigin: true,
    // pathRewrite: { '^/api': '' }, // 把路径/api 替换成空字符, 后端服务没有这个前缀
  }
}
