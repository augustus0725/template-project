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
