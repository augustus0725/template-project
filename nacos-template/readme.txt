1. 下面的值没有设置导致nacos起不来
nacos.core.auth.plugin.nacos.token.secret.key=VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=
2. 默认是不会自动注册, 需要手动设置一下
nacos.discovery.auto-register=true
3. ip 会随机选, 需要显示指定
nacos.discovery.register.ip=192.168.0.161
4. 需要设置application.name