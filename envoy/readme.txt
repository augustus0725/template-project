# run
envoy -c envoy-demo.yaml

# 可以添加覆盖的配置
--config-yaml "$(cat envoy-override.yaml)"

# validate envoy的配置
envoy --mode validate -c my-envoy-config.yaml

# 文件的日志 
envoy -c envoy-demo.yaml --log-path logs/custom.log
== 配置里应该也可以配置日志, 下面这段是日志打印到控制台
access_log:
- name: envoy.access_loggers.stdout
  typed_config:
    "@type": type.googleapis.com/envoy.extensions.access_loggers.stream.v3.StdoutAccessLog

	
== 日志级别 ： envoy -c envoy-demo.yaml -l info
trace

debug

info

warning/warn

error

critical

off
	
# envoy 的逻辑

== 配置路由  "/" ===> cluster: service_envoyproxy_io
virtual_hosts:
- name: local_service
  domains: ["*"]
  routes:
  - match:
      prefix: "/"
    route:
      host_rewrite_literal: www.envoyproxy.io
      cluster: service_envoyproxy_io

== cluster 里有许多 nginx 的service
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
				
# tls的方法
transport_socket:
      name: envoy.transport_sockets.tls
      typed_config:
        "@type": type.googleapis.com/envoy.extensions.transport_sockets.tls.v3.UpstreamTlsContext
        sni: www.baidu.com

# envoy的抽象
static_resources:   --> 静态的配置, 对应的还有动态的配置

listeners:
  - name: listener_0 --> 对应了一个进程端口

clusters:  --> 里面放置了一堆的service, 负载均衡可以在这一层


# 动态配置
dynamic_resources:
  cds_config:
    path: /var/lib/envoy/cds.yaml
  lds_config:
    path: /var/lib/envoy/lds.yaml

# 配置admin管理, 能看到各种统计
admin:
  address:
    socket_address:
      address: 0.0.0.0
      port_value: 9901