# ���� chart ģ��
helm create mychart


mychart/Chart.yaml : ����Ӧ�õ�Ԫ���ݣ� ����/�汾..., ������chart
mychart/values.yaml : Ӧ����Ҫ��һЩ����, ����ģ���������  (���Ա�����)
mychart/templates
- hpa.yaml : �Զ���չ������ (���η����)
- deployment.yaml : ������ô����
- ingress.yaml : ����ע��
- service.yaml ��������nginx��upstream (ha)
- serviceaccount.yaml

