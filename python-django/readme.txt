# install
python3 -m venv venv
pip install Django

# 初始化项目
django-admin startproject mysite
--> 或者 python3 -m django startproject mysite

# 依赖关系
wsgi -> settings -> urls
manage 管理

# 管理类
@ 启动
python manage.py runserver 0:8000
@ 创建app
python manage.py startapp polls
@ 数据库部分 - postgresql数据库
pip install psycopg2
---------------------------
DATABASES = {
    'default': {
        # 'ENGINE': 'django.db.backends.sqlite3',
        # 'ENGINE': 'django.db.backends.mysql',
        # 'ENGINE': 'django.db.backends.oracle',
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'django',
        'USER': 'pgadmin',
        'PASSWORD': '******',
        'HOST': '121.*.*.*',
        'PORT': '5432',
    }
}
---------------------------
python manage.py migrate -> 根据设置和我们构建的模型来创建数据库表结构
--> 在modals里定义表结构（类的方式）
--> 在setting里注册polls应用
python manage.py makemigrations polls --> 在polls\migrates目录生成schema的增量 0001_initial.py
python manage.py migrate --> 更新数据库表结构


@ 命令行 -> 打开命令行，可以做一些调试，比如尝试操作数据库的api
python manage.py shell

----------------------------------------------
>>> from polls.models import Choice, Question
>>> Question.objects.all()
----------------------------------------------

# 学习django的插件
@ admin  --> django.contrib.admin
python manage.py createsuperuser --> 创建超级用户
   http://127.0.0.1:8000/admin/

--> 在polls的admin.py里注册model, 之后这个model就可以在admin界面维护（增删改查）

@ 测试 （会自动创建测试库，表，测试完成之后删除，）
python manage.py test polls


# 部署相关
@ centos
yum install -y libpqxx-devel  # 添加pg驱动的时候需要安装这个
yum install -y gcc
yum install -y python3-devel
pip install psycopg2
yum install -y python36-gunicorn

运行
gunicorn <project_name>.wsgi
gunicorn-3 -w 4 -b 192.168.31.163:8000 mysite.wsgi