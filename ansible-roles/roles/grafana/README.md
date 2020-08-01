默认的访问地址是：http://<ip>:<port>/grafana

自动化部署完成之后需要先配置数据源，选择Prometheus，然后把host-monitor.json文件导入

页面发布的地址, 可以在iframe里配置
http://<ip>:<port>/grafana/d/9CWBz0bik/zhu-ji-zhuang-tai?orgId=1&refresh=15s&kiosk

建议：将上面的地址用web所在的nginx代理,这样就可以只使用相对地址 
/grafana/d/9CWBz0bik/zhu-ji-zhuang-tai?orgId=1&refresh=15s&kiosk

nginx里需要添加的代理：

```
        location /grafana {
            proxy_pass http://<ip>:<port>;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-Host $host;
            proxy_set_header X-Forwarded-Server $host;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
```

