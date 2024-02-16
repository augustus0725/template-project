# ����ϵͳ ubuntu 2204 server

# ��װ�������
sudo apt install xfce4 xfce4-goodies

# ��װvnc����
sudo apt install tigervnc-standalone-server tigervnc-common tigervnc-tools

# ����û�
useradd darwin
passwd darwin  <- ��������

# �л��û�, ����vnc�����һЩ����
su - alice
vncserver   <-- �ύ����һЩ����

vncserver -kill :1  <-- ���֮��ֹͣ�Ự

# ������������
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

# ����vnc�û�
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

# ����vnc����
systemctl enable tigervncserver@:1.service
systemctl start tigervncserver@:1.service

# ���������ÿ����޸�������ļ� (Ȩ����ص�, �������ֻ��localhost��¼,������ص�)
/etc/tigervnc/vncserver-config-mandatory


# ��ȫ���
## ssh ת������֤���ݴ�������еİ�ȫ
ssh -L 5901:127.0.0.1:5901 -N -f -l alice t 192.168.10.15

## ���ƴӷ�������������
$SendCutText="no";

## ����ֻ��localhost��¼
$localhost = "yes";

## ���ƴӿͻ��˿������ݵ�������
$AcceptCutText="yes";

# ��������ص�
��������vnc�ͻ���
