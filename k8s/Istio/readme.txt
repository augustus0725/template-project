# ʹ��helm����kubectl��Ҫ��yaml�ļ�, ��ֱ��ʹ��helm���Կ����м���, �������޸�
====> 1
helm template install/kubernetes/helm/istio \
--name istio --namespace istio-system \
-f my-values.yaml > my-istio.yaml
====> 2
kubectl apply -f my-istio.yaml
====> 3 Ӧ�� istioctl ע��һЩsidecar������, ���� kubectl��yaml
istioctl kube-inject -f my.app.yaml | kubectl apply -f -
====> 4. ����һЩת������