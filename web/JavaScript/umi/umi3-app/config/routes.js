// const的方式导致路由失效
export default [
  {
    path: '/',
    // 与子路由之间加一层layout组件
    component: '@/layouts/base-layouts',
    routes: [
      { path: '/login', component: '@/pages/login'},
      { path: '/reg', component: '@/pages/reg'},
      { path: '/', component: '@/pages/index' },
      // 内容展示区
      {
        path: '/goods',
        component: '@/layouts/aside-layouts',
        routes: [
          {
            path: '/goods/list',
            component: '@/pages/goods/list',
          },
          {
            path: '/goods',
            component: '@/pages/goods',
          },
        ],
      }

    ]
  },

  // 自上而下匹配
  {
    component: '@/pages/404'
  }
];
