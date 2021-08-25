# 环境
pip3 install Scrapy
-> 出错 (Windows 安装出错)
-> 切换到ubuntu 18.04
-> 出错
pip3 install setuptools-rust
-> 出错, 先升级pip
pip3 install --upgrade pip
-> 继续安装
-> ok

# 测试
scrapy runspider lesson01/quotes_spider.py -o quotes.jl
-> ok
