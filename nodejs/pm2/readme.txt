#
npm install pm2@latest -g

# 开机启动
pm2 startup

# 管理进程
pm2 start -n toolmore next -- start -p 80
pm2 logs --lines 200

#
pm2 save