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