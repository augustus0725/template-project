# orm模型
pnpm add prisma
pnpm add prisma/client

# 创建lib目录, 里面放一些util
pnpm add prisma
pnpm add @prisma/client
pnpm add @auth/prisma-adapter

db.js

==> pnxp prisma init 生成数据库的配置
- 在.env文件里设置 DATABASE_URL
- 在schema.prisma 里设置 database block
  - postgresql
  - mysql
  - sqlite
  - sqlserver
  - mongodb
  - cockroachdb
- 运行 pnxp prisma db pull 将数据库里的schema写到prisma schema
- 运行 pnxp prisma generate 生成 prisma client 执行数据库操作
- 运行 pnxp prisma db push 在数据库内生成表结构

==> bcrypt: 对密码加密
pnpm add @types/bcrypt

--------------------------------
使用next-auth v5 的版本 
--------------------------------
pnpm add next-auth@beta 

--------------------------------
独立学习 prisma 
--------------------------------
pnpm add prisma
pnpx prisma init --database-provider postgresql

==> 添加一些model之后
pnpx prisma migrate dev --name init
// 删掉数据重置
pnpx prisma migrate reset
// 可以启动一个web,查看数据库的结构
pnpx prisma studio


--------------------------------
独立学习 zod  (对输入的数据进行验证)
--------------------------------
pnpm add zod
// 这个也具有基础的validate能力, 但是功能没有zod全, 比如email的检测
pnpm add react-hook-form
// 适配 zod & react-hook-form
pnpm add @hookform/resolvers


---------------------------------------
独立学习 tailwindcss -> tailwind-merge
---------------------------------------
// 安装
pnpm add tailwindcss-merge
// 子组件通过下面的方法覆盖样式
const className = twMerge('border rounded px-2 py-1', props.className)
return <input {...props} className={className} />

---------------------------------------------
独立学习 tailwindcss -> tailwindcss-animate
---------------------------------------------
// 安装
pnpm add tailwindcss-animate
// 配置 tailwind.config.js
module.exports = {
	theme: {
		// ...
	},
	plugins: [
		require("tailwindcss-animate"),
		// ...
	],
}

// 有一些动画效果
<!-- Add an animated fade and zoom entrance -->
<div class="animate-in fade-in zoom-in">...</div>

<!-- Add an animated slide to top-left exit -->
<div class="animate-out slide-out-to-top slide-out-to-left">...</div>

<!-- Control animation duration -->
<div class="... duration-300">...</div>

<!-- Control animation delay -->
<div class="... delay-150">...</div>

----------------------------------------------------------------
独立学习 clsx
utils方法, 合并css属性用的, 可以和tailwindcss结合
----------------------------------------------------------------
clsx('foo', [1 && 'bar', { baz:false, bat:null }, ['hello', ['world']]], 'cya');
//=> 'foo bar hello world cya'

import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

----------------------------------------------------------------
next-auth里和github对接
----------------------------------------------------------------
callback的url应该是: http://localhost:3001/api/auth/callback/github
==> 所有的callback url可以从 http://localhost:3001/api/auth/providers 获取

----------------------------------------------------------------
next-auth里自己自定义的credentials
----------------------------------------------------------------

----------------------------------------------------------------
next-auth 和 prisma的绑定, 实现通过表单来验证身份
----------------------------------------------------------------
pnpm add @auth/prisma-adapter
// 对表单上的密码进行加密
pnpm add bcryptjs







