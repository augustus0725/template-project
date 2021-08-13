# -*- coding: utf-8 -*-
import os

from flask import Flask, url_for, render_template, request, make_response
from markupsafe import escape
from werkzeug.exceptions import abort
from werkzeug.middleware.proxy_fix import ProxyFix
from werkzeug.utils import secure_filename

app = Flask(__name__)

# 应用中间件
# ProxyFix middleware for running behind Nginx
app.wsgi_app = ProxyFix(app.wsgi_app)



@app.route('/')
def index():
    return 'Hello, World!'


@app.route('/user/<username>')
def show_user_profile(username):
    # show the user profile for that user
    return 'User %s' % escape(username)


@app.route('/post/<int:post_id>')
def show_post(post_id):
    # show the post with the given id, the id is an integer
    return 'Post %d' % post_id


@app.route('/path/<path:subpath>')
def show_subpath(subpath):
    # show the subpath after /path/
    return 'Subpath %s' % escape(subpath)


# 路径上的参数可以指定类型

# [string] (default) accepts any text without a slash
# [int] accepts positive integers
# [float] accepts positive floating point values
# [path] like string but also accepts slashes
# [uuid] accepts UUID strings


# 尾部有/, 访问 /projects 的时候会被自动补全成 /projects/
@app.route('/projects/')
def projects():
    return 'The project page'


# 尾部没有/, 不会补全, 访问/about/会报404错误
@app.route('/about')
def about():
    return 'The about page'


def valid_login(param, param1):
    pass


def log_the_user_in(param):
    pass


@app.route('/login', methods=['GET', 'POST'])
def login():
    error = None
    if request.method == 'POST':
        if valid_login(request.form['username'],
                       request.form['password']):
            return log_the_user_in(request.form['username'])
    else:
        error = 'Invalid username/password'
    # the code below is executed if the request method
    # was GET or the credentials were invalid
    return render_template('login.html', error=error)


# ?key=value
# searchword = request.args.get('key', '')


@app.route('/user/<username>')
def profile(username):
    return '{}\'s profile'.format(escape(username))


@app.route('/hello/')
@app.route('/hello/<name>')
def hello(name=None):
    # 获取 cookies
    # username = request.cookies.get('username')
    # 设置 cookies
    # resp = make_response(render_template(...))
    # resp.set_cookie('username', 'the username')
    return render_template('hello.html', name=name)


@app.route('/upload', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        f = request.files['the_file']
        # f.save('/var/www/uploads/uploaded_file.txt')
        # 能够获取文件在客户端的名字
        f.save('/var/www/uploads/' + secure_filename(f.filename))
        # abort(401)


# 重定向
# redirect(url_for('login'))
# abort(401)
# 自定义错误页面
@app.errorhandler(404)
def page_not_found(error):
    return render_template('page_not_found.html'), 404


def get_current_user():
    class User:
        def __init__(self):
            self.username = None
            self.theme = None
            self.image = None

    return User()


# JSON api
@app.route("/me")
def me_api():
    user = get_current_user()
    # 使用flask 预想配置好的日志工具
    app.logger.debug('A value for debugging')
    app.logger.warning('A warning occurred (%d apples)', 42)
    app.logger.error('An error occurred')
    return {
        "username": user.username,
        "theme": user.theme,
        "image": url_for('profile', username='user.image'),
    }


# 几种返回值的处理方法

# 1. If a response object of the correct type is returned it’s directly returned from the view.
# 2. If it’s a string, a response object is created with that data and the default parameters.
# 3. If it’s a dict, a response object is created using jsonify.
# 4. If a tuple is returned the items in the tuple can provide extra information. Such tuples have to be in
# the form (response, status), (response, headers), or (response, status, headers).
# The status value will override the status code and headers can be a list or dictionary of additional header
# values.
# 5. If none of that works, Flask will assume the return value is a valid WSGI application and convert that into a
# response object.

# make_response 产生 response object
# resp = make_response(render_template('error.html'), 404)
# resp.headers['X-Something'] = 'A value'


# session 采用cookie实现, cookie的值是加密的, 所以client端看不到真实的值, 另外需要 设置 app的安全key
app.SECRET_KEY = os.urandom(16)
# 保存变量到 session
# session['username'] = request.form['username']
# 删除 session
# session.pop('username', None)


with app.test_request_context():
    print(url_for('index'))
    print(url_for('login'))
    print(url_for('login', next='/'))
    print(url_for('profile', username='John Doe'))
    print(url_for('static', filename='style.css'))
