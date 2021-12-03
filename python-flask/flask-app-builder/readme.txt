# 为了快速构建应用

# 环境
pip3 install flask-appbuilder

# 
flask fab create-app
set FLASK_APP=app
flask fab create-admin

# 思维方式 (例子看【抽象】目录)
View
--BaseView 不带数据库,不包含表单
--FormView  不带数据库, 包含表单
--ModelView
  MultipleView: 可以把几个view组合起来
  MasterDetailView: 和名字一样，主以及描述主的view组合起来

API(REST)
--BaseAPI
--ModelRestApi

FAB_API_SWAGGER_UI = True # 让页面可看swagger api说明
                          # http://127.0.0.1:5000/swagger/v1
                          # 可以在方法前添加 @protect() 来采用 jwt 来保护接口
                          # 那样就需要在 请求头 添加token, 先用接口请求
---------------------------------------------------------------------------
# 请求token
   $ curl -XPOST http://localhost:8080/api/v1/security/login -d \
  '{"username": "ray", "password": "123456", "provider": "db", "refresh": true}' \
  -H "Content-Type: application/json"

# 请求的时候可以带自动刷新
“refresh”: true
---------------------------------------------------------------------------
# 带token访问api
$ curl 'http://localhost:8080/api/v1/example/private' -H "Authorization: Bearer $TOKEN"
{
    "message": "This is private"
}

# jwt的设置
https://flask-jwt-extended.readthedocs.io/en/latest/options/
---------------------------------------------------------------------------


Chart
-- GroupByChartView


各种功能元素可以通过

appbuilder.add_view 绑定到菜单栏
appbuilder.add_api 注册api

# 主要需要学习的就是如何使用上面的抽象以及定制上面的抽象了


# appbuilder/base.html
{% extends base_template %}
这个模板里的base_template是变量, 
构建AppBuilder的对象传入这个参数
base_template="appbuilder/baselayout.html",

# 在view里定义action可以在页面对某条记录做操作
    @action(name='action1', text='action text', multiple=True, single=True)
    def action1(self, items):
        print(f"{type(items)} : {items}")
        self.update_redirect()
        return redirect(self.get_redirect())
