推荐使用通用方法

tox -e release

```
需要在 package.list 里写依赖数据目录
依赖的lib默认会copy到打包项目的根目录
运行依赖bin/run.py 这个需要定制一下， 主要是调用入口函数
```


发行软件

不带python python各个平台可以单独安装

python setup.py bdist --format=zip

可以用的格式有

wininst
rpm
msi