# 安装环境
python3 -m venv venv
pip3 install Flask

# 运行
set FLASK_ENV=development
set FLASK_APP=flaskr
flask run

# 配置数据库
新建 db.py
flask init-db 初始化库 --> 这个有问题, 没有django里在python代码层面先定义model, 不应该看到sql, 看到sql通用性就不太好

# 笔记

##
app.teardown_appcontext() tells Flask to call that function when cleaning up after returning the response.
app.cli.add_command() adds a new command that can be called with the flask command.

## flask 用 Blueprints 来组织views 来构建大的项目
Blueprints