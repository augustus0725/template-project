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

## 节点上安装Greenplum ETL组件

```
# 安装下面的rpm包
yum install -y gpss-gpdb6-1.7.2-rhel7-x86_64.rpm
# 配置环境变量
source /usr/local/gpss/gpss_path.sh
# 这样一些ETL设施就齐全了
gpkafka gpss gpsscli kafkacat
```

## ETL

```
# 流的方式导入数据
https://greenplum.docs.pivotal.io/streaming-server/1-3-6/instcfgmgt.html

# spark的方式导入数据
https://docs.vmware.com/en/VMware-Tanzu-Greenplum-Connector-for-Apache-Spark/2.1/tanzu-greenplum-connector-spark/GUID-index.html
```

