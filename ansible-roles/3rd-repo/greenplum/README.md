# Add greenplum binary.

# 

```shell
# 6.21.0
open-source-greenplum-db-6.21.0-rhel7-x86_64.rpm

# 6.23.3 功能完整一点
greenplum-db-6.23.3-rhel7-x86_64.rpm
greenplum-cc-web-6.8.4-gp6-rhel7-x86_64.tar.gz
pxf-gp6-6.5.0-2.el7.x86_64.rpm
```

# gpfdist
```shell
# 启动文件服务
/usr/local/greenplum-db-6.21.0/bin/gpfdist -p 8081 -d /opt/data/gpfdist-data/ -l /opt/data/log/run.out
# 建外表
create external table stu(name text, age int8)
location ('gpfdist://192.168.0.184:8081/*.csv')
format 'CSV' (DELIMITER ',');
# 查询/ insert into x select * from stu; 
select * from stu;
```







