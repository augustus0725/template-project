# 操作系统 ubuntu 2204 server

# 安装桌面程序
sudo apt install xfce4 xfce4-goodies

# 安装vnc服务
sudo apt install tigervnc-standalone-server tigervnc-common tigervnc-tools

# 添加用户
useradd darwin
passwd darwin  <- 设置密码

# 切换用户, 设置vnc密码和一些配置
su - alice
vncserver   <-- 会交互做一些配置

vncserver -kill :1  <-- 完成之后停止会话

# 配置启动桌面
vim ~/.vnc/xstartup
-----------------------------------------------------
#!/bin/sh
# Start up the standard system desktop
unset SESSION_MANAGER
unset DBUS_SESSION_BUS_ADDRESS
/usr/bin/startxfce4
[ -x /etc/vnc/xstartup ] && exec /etc/vnc/xstartup
[ -r $HOME/.Xresources ] && xrdb $HOME/.Xresources
x-window-manager &
-----------------------------------------------------
chmod +x ~/.vnc/xstartup

# 配置vnc用户
vim /etc/tigervnc/vncserver.users
-----------------------------------------------------
:1=darwin
-----------------------------------------------------

cp /usr/lib/systemd/system/tigervncserver@.service /etc/systemd/system/tigervncserver@:1.service
-----------------------------------------------------
[Unit]
Description=Remote desktop service (VNC)
After=network.target

[Service]
Type=forking
WorkingDirectory=/home/darwin
ExecStart=/usr/libexec/tigervncsession-start %i
PIDFile=/run/tigervncsession-%i.pid
SELinuxContext=system_u:system_r:vnc_session_t:s0

[Install]
WantedBy=multi-user.target
-----------------------------------------------------

systemctl daemon-reload

# 启动vnc服务
systemctl enable tigervncserver@:1.service
systemctl start tigervncserver@:1.service

# 其他的配置可以修改下面的文件 (权限相关的, 比如控制只能localhost登录,拷贝相关的)
/etc/tigervnc/vncserver-config-mandatory


# 安全相关
## ssh 转发来保证数据传输过程中的安全
ssh -L 5901:127.0.0.1:5901 -N -f -l alice t 192.168.10.15

## 控制从服务器里拷贝数据
$SendCutText="no";

## 控制只能localhost登录
$localhost = "yes";

## 控制从客户端拷贝数据到服务器
$AcceptCutText="yes";

# 清晰度相关的
可以设置vnc客户端
