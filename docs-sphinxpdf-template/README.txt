# 安装
- 安装python
- 安装pip
- 安装依赖
  pip install sphinx
  pip install recommonmark

# 书写文档
1.每一章的文档用markdown写，比如 安装.md, 放在目录source里
2.在source/index.rst里包含这些章节
3.使用到的图片，放到source/_static, md文档里用相对位置引用这些文件

# 构建网站格式
.\make.bat html (windows)
make html (linux) 


# 构建pdf格式(只有linux支持)
make latexpdf