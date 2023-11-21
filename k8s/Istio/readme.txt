# 使用helm生成kubectl需要的yaml文件, 不直接使用helm可以看到中间物, 可以做修改
====> 1
helm template install/kubernetes/helm/istio \
--name istio --namespace istio-system \
-f my-values.yaml > my-istio.yaml
====> 2
kubectl apply -f my-istio.yaml
====> 3 应用 istioctl 注入一些sidecar的依赖, 生成 kubectl的yaml
istioctl kube-inject -f my.app.yaml | kubectl apply -f -
====> 4. 配置一些转发规则