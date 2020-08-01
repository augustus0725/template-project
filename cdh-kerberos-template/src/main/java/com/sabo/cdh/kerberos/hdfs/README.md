cdh里开启Kerberos之后hdfs账户的使用

1. hdfs是/sbin/nologin
   使用命令 chsh -s /bin/bash hdfs 让用户可以登录
2. 给hdfs一个Kerberos账户
   kadmin.local -q "addprinc hdfs@HONGWANG.COM"
3. su hdfs
   kinit hdfs@HONGWANG.COM   
   之后就可以用hdfs账户(最高权限)操作hdfs   