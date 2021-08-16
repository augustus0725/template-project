Flask Web 开发的大型应用的示例

# 环境
python3 -m venv venv
pip3 install -r requirements.txt

# run
$env:FLASK_APP = "flasky.py"
$env:FLASK_ENV = "development"

flask db init
flask db migrate -m "init migration"
flask db upgrade

# 继续学习flask的扩展

# flask-login
pip3 install flask-login
--
flask-login 需要User对象, 并且要求里面有需要的属性和方法 --> 有更简单的实现, 让User继承UserMixin就可以
is_authenticated
is_active
is_anonymous
get_id()
