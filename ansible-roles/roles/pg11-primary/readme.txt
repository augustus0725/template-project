1. 注意postgresql默认的用户postgres的密码是空，要手动改一下
2. 把postgres用户变成无法登陆的用户
   usermod -s /sbin/nologin postgres
3. 把用户的认证方式变成md5
   修改pg_hba.conf 文件
   trust方式不检测密码
4. 开启了归档之后, 要设置定时任务, 避免归档过多
   0 4 * * * tmpwatch -v 7d /opt/data/pgsql/pg_archive