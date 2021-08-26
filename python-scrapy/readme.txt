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

>> 一些专用的爬虫
- CrawlSpider
  能自动follow页面上面的链接, 可以和规则结合起来用
- XMLFeedSpider/CSVFeedSpider
  特殊格式的爬虫
- SitemapSpider
  支持使用robots.txt文件去爬取数据, 自动发现链接, 使用正则关联<url, parser>, 能使用一些过滤

>> 爬取的数据Item处理流
   ! 可以定义Pipeline Processor
   class JsonWriterPipeline:

    def open_spider(self, spider):
        self.file = open('items.jl', 'w')

    def close_spider(self, spider):
        self.file.close()

    def process_item(self, item, spider):
        line = json.dumps(ItemAdapter(item).asdict()) + "\n"
        self.file.write(line)
        return item

    ! 然后把具体的流注册到pipeline, 数字800是优先级, 范围0~1000
    ITEM_PIPELINES = {
    'myproject.pipelines.JsonWriterPipeline': 800,
}

>> 配置setting可以让产生的Items归档到文件, 文件可以放到FTP, 本地文件以及各种云存储

>> IP受限的问题可以使用 HttpProxyMiddleware

   尝试使用Google Cache避免直接访问目标网站

   可以对接IP池
   - 免费
   https://www.torproject.org/
   https://scrapoxy.io/
   - 收费 (可以找找国内的付费版本)
   https://proxymesh.com/

>> 启动方式
   - 从命令行启动
   - 程序里指定爬虫
     process = CrawlerProcess(settings={
          "FEEDS": {
          "items.json": {"format": "json"},
        },
     })

     process.crawl(MySpider)
     # process.crawl(MySpider1)
     # process.crawl(MySpider2)
     process.start()



