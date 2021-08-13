# 安装环境
python3 -m venv venv
pip3 install Flask

# 运行

-- Linux
export FLASK_APP=hello.py
-- Windows cmd
set FLASK_APP=hello.py
-- power shell
$env:FLASK_APP = "hello.py"

flask run --host=0.0.0.0
python -m flask

-- 测试模式
export FLASK_ENV=development
FLASK_DEBUG=1

# 应用 flask 的 【中间件】 和 【扩展】 可以快速帮我们构建应用
中间件比如有：werkzeug.middleware.proxy_fix
扩展：       Flask-SQLAlchemy