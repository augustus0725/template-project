学习 《Flask Web开发》

# 环境
pip3 install flask

set FLASK_APP=hello.py
set FLASK_ENV=development

# 运行
flask run

# 学习扩展
pip3 install flask-bootstrap
-- 不同国家的时区不一样, 一种通用的方案是： 后台服务的时间采用UTC, utc时间发送给Web浏览器的时候做本地化的转换
pip3 install flask-moment
-- 使用表单
pip3 install flask-wtf
-- 使用数据库
pip3 install flask-sqlalchemy
-- 使用数据库迁移框架, 内部实现是alembic, 增加了 flask db 命令       <Perform database migrations.>
pip3 install flask-migrate
--> flask db init --> 产生migrates目录
!! 一般流程
- 修改模型
- flask db migrate -m "init migration" -> 产生迁移脚本
- 检查脚本, 并把脚本放到版本控制
- flask db upgrade -> 应用迁移

-- 发送邮件的扩展, 暂时不学
pip3 install flask-mail
