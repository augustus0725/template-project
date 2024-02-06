# orm模型
pnpm add prisma
pnpm add prisma/client

# 创建lib目录, 里面放一些util
pnpm add prisma
pnpm add @prisma/client
pnpm add @auth/prisma-adapter

db.js

==> zod 对输入的数据进行验证

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
- 运行 pnxp prisma studio 可以启动一个web,查看数据库的结构

==> bcrypt: 对密码加密
pnpm add @types/bcrypt


--------------------------------
使用next-auth v5 的版本 
--------------------------------
 
