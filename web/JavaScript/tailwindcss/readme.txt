# 准备环境, 用cra脚手架
pnpm add --save-dev create-react-app
pnpx create-react-app tailwind-app
# 添加tailwind依赖
pnpm add -D tailwindcss postcss autoprefixer

# 配置 tailwind
pnpx tailwindcss init -p
-> 主要配置需要转换的css文件
-> 主要配置purge功能, 能让应用只使用自己用到的css, 尽可能产生小的包
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  
  
# 学习笔记
- flex 布局 和 grid 布局是 两种不同的布局
- justify-* (justify-start/justify-end...) 同时可以操作 flex items 和 grid items
  justify-items 只用来控制 grid items
  align content-* (content-center/content-start ...) 控制多行（flex & grid）
  align item-* 控制单行 竖直方向 start center end （flex & grid）
  place content/items (基本是操作 grid的)
