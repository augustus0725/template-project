##### 文档

```shell
# 我们文档用shphix开发的， 除了主文档index.rst, 其他文档采用markdown语法

# 安装python3
http://192.168.0.185:15003/common/%E5%BC%80%E5%8F%91%E5%B8%B8%E7%94%A8%E8%BD%AF%E4%BB%B6/python-3.6.8-amd64.exe
# 安装依赖
pip3 install sphinx
pip3 install recommonmark

# 使用Markdown语法书写文档
用你喜欢的md编辑器写文档(docs/sources目录下), 写完之后把文件名注册到docs/sources/index.rst里

# 编译
.\make.bat html      --> build 目录下会生成html格式的文档, 进入build/html目录, 用浏览器浏览index.html

```

##### API

```shell
# swagger本地地址
# 本地
http://127.0.0.1:8080/doc.html
```
