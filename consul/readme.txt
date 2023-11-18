# 开发模式
consul.exe -dev

#  角色
client:    用于微服务向consul提交请求, 类似代理或者k8s的service, 帮处理负载均衡
server:    实现注册, 发现, 配置之类的逻辑
