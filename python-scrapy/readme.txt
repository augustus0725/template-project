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

>> 安装
Anaconda3-2021.05
>> 使用pycharm创建anaconda虚拟环境
-> python 3.9.6
-> pip3 install Scrapy 能直接成功

# 测试
scrapy runspider lesson01/quotes_spider.py -o quotes.jl
-> ok


# 开始学习

>> 直接使用scrapy startproject 创建项目会有权限问题 (Windows下) -> 使用anaconda创建的虚拟环境没有出现问题
$ cd /tmp
$ scrapy startproject demo_pro
$ cp -r demo_pro /mnt/d/Scrapy


scrapy startproject tutorial
cd tutorial
-> 在spiders目录里创建标准的爬虫
-> scrapy crawl quotes    -> 可以下载html

>> 爬虫比较难得就是怎么定位网页元素, scrapy 提供了控制台程序让我们做测试
scrapy shell "http://quotes.toscrape.com/page/1/"
>>> response.xpath('//title')
>>> response.css('title::text').get()

>> 修改parse的代码
scrapy crawl quotes -O quotes.json

>> 执行scrapy命令可以传递参数到spider, 增加一些定制能力和灵活性
scrapy crawl quotes -O quotes-humor.json -a tag=humor
-> -a tag=humor   传递了tag参数
-> 可以在spider的子类里获取tag
   tag = getattr(self, 'tag', None)
-> 可以在spider的__init__方法里获取
   def __init__(self, tag=None, *args, **kwargs)

>> python scrapy 的例子  https://github.com/scrapy/quotesbot

>> 页面需要登录的情况
scrapy.FormRequest("http://www.example.com/login",
                                   formdata={'user': 'john', 'pass': 'secret'},
                                   callback=self.logged_in)
>> 页面不需要登录
scrapy.Request(url=url, callback=self.parse)