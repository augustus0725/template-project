# run
envoy -c envoy-demo.yaml

# ������Ӹ��ǵ�����
--config-yaml "$(cat envoy-override.yaml)"

# validate envoy������
envoy --mode validate -c my-envoy-config.yaml

# �ļ�����־ 
envoy -c envoy-demo.yaml --log-path logs/custom.log
== ������Ӧ��Ҳ����������־, �����������־��ӡ������̨
access_log:
- name: envoy.access_loggers.stdout
  typed_config:
    "@type": type.googleapis.com/envoy.extensions.access_loggers.stream.v3.StdoutAccessLog

	
== ��־���� �� envoy -c envoy-demo.yaml -l info
trace

debug

info

warning/warn

error

critical

off
	
# envoy ���߼�

== ����·��  "/" ===> cluster: service_envoyproxy_io
virtual_hosts:
- name: local_service
  domains: ["*"]
  routes:
  - match:
      prefix: "/"
    route:
      host_rewrite_literal: www.envoyproxy.io
      cluster: service_envoyproxy_io

== cluster ������� nginx ��service
  - name: service_envoyproxy_io
    type: LOGICAL_DNS
    # Comment out the following line to test on v6 networks
    dns_lookup_family: V4_ONLY
    load_assignment:
      cluster_name: service_envoyproxy_io
      endpoints:
      - lb_endpoints:
        - endpoint:
            address:
              socket_address:
                # address: www.envoyproxy.io
                address: 192.168.0.185
                port_value: 15003
				
# tls�ķ���
transport_socket:
      name: envoy.transport_sockets.tls
      typed_config:
        "@type": type.googleapis.com/envoy.extensions.transport_sockets.tls.v3.UpstreamTlsContext
        sni: www.baidu.com

# envoy�ĳ���
static_resources:   --> ��̬������, ��Ӧ�Ļ��ж�̬������

listeners:
  - name: listener_0 --> ��Ӧ��һ�����̶˿�

clusters:  --> ���������һ�ѵ�service, ���ؾ����������һ��


# ��̬����
dynamic_resources:
  cds_config:
    path: /var/lib/envoy/cds.yaml
  lds_config:
    path: /var/lib/envoy/lds.yaml

# ����admin����, �ܿ�������ͳ��
admin:
  address:
    socket_address:
      address: 0.0.0.0
      port_value: 9901