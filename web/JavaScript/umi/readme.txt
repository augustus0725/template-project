# 研究下umi的结构, 研究两个版本 umi3/umi4
# 使用脚手架创建umi-app, 这个是umi3
  mkdir umi3-app
  cd umi3-app
  pnpm add --save-dev @umijs/create-umi-app
  npx @umijs/create-umi-app  --> 创建项目
  pnpm i                     --> 安装依赖
  pnpm start                 --> 测试
  

# (官网) 用create-umi脚手架程序创建umi4项目
mkdir umi4-app && cd umi4-app
pnpm dlx create-umi@4.0.20 --no-git --no-install


# 也可以使用ant-design/pro-cli 脚手架来创建项目
pnpm dlx @ant-design/pro-cli create ant-design-app
-> 选择 umi4/umi3
   这里选择的umi4 -> 选择umi4 只能用ant-design pro的脚手架，不能选Simple
   