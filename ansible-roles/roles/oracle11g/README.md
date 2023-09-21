假设依赖的源已经配好了
# 官网： http://public-yum.oracle.com/oracle-linux-7.html

[UEK]
name=UEK
enabled=1
failovermethod=priority
baseurl=https://yum.oracle.com/repo/OracleLinux/OL7/UEKR6/x86_64/
gpgcheck=0

[oracle]
name=oracle
enabled=1
failovermethod=priority
baseurl=https://yum.oracle.com/repo/OracleLinux/OL7/latest/x86_64
gpgcheck=0


# 需要先在/etc/hosts里修改hostname和ip对应关系