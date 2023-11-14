# 创建 chart 模板
helm create mychart


mychart/Chart.yaml : 声名应用的元数据， 名字/版本..., 依赖的chart
mychart/values.yaml : 应用需要的一些变量, 这样模板里就能用  (可以被覆盖)
mychart/templates
- hpa.yaml : 自动扩展的配置 (自治方面的)
- deployment.yaml : 描述怎么部署
- ingress.yaml : 服务注册
- service.yaml ：类似于nginx的upstream (ha)
- serviceaccount.yaml

