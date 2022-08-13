## 自动部署的依赖

```
os: CentOS Linux release 7.6.1810

# 问题, 有的包版本比较高，需要降级
# krb5-libs.x86_64 0:1.15.1-37.el7_6 -> krb5-libs.x86_64 0:1.15.1-34.el7
[root@srv01 vagrant]# rpm -qa | grep krb5-libs
krb5-libs-1.15.1-37.el7_6.x86_64
[root@srv01 vagrant]# yum downgrade krb5-libs -y
Loaded plugins: fastestmirror
Loading mirror speeds from cached hostfile
Resolving Dependencies
--> Running transaction check
---> Package krb5-libs.x86_64 0:1.15.1-34.el7 will be a downgrade
---> Package krb5-libs.x86_64 0:1.15.1-37.el7_6 will be erased

```

