1. 注意postgresql默认的用户postgres的密码是空，要手动改一下
2. 把postgres用户变成无法登陆的用户
   usermod -s /sbin/nologin postgres