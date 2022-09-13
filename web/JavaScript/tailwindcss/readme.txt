# 准备环境, 用cra脚手架
pnpm add --save-dev create-react-app
pnpx create-react-app tailwind-app
# 添加tailwind依赖
pnpm add --save-dev tailwindcss@npm:@tailwindcss/postcss7-compat postcss@^7 autoprefixer@^9
# cra不能直接覆盖postcss的配置, 用craco包装一下
pnpm add --save-dev @craco/craco@7.0.0-alpha.7

-> 替换
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  }
-> 到
  "scripts": {
    "start": "craco start",
    "build": "craco build",
    "test": "craco test",
    "eject": "react-scripts eject"
  }
  
配置craco.config.js文件 => 配置 postcss

# 配置 tailwind
pnpx tailwindcss-cli@latest init  
-> 主要配置purge功能, 能让应用只使用自己用到的css, 尽可能产生小的包
