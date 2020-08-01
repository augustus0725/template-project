步骤

1. kadmin.local添加用户a生成keytab文件
2. kafka赋权
   kafka-acls --add --allow-principal user:sabo --operation ALL --topic topica --authorizer-properties zookeeper.connect=192.168.0.110:2181
