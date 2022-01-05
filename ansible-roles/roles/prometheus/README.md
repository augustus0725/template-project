# 注意
```
Prometheus实例选取两台机器，相同抓取配置，采用nginx做HA
考虑prometheus数据的时候要记得时序数据库，数据的格式
<时间戳>: v
```

# 启动参数

```shell
--config.file          配置文件
----storage.tsdb.path  数据存放路径
```

# 数据模型

## 介绍

```shell
# 每一条数据有一个metric name 和 多个 label组成
# <metric name> 类似实体表
# <label name>=<label value> 类似维度表

<metric name>{<label name>=<label value>, ...}

# eg： api_http_requests 比较泛, 加上维度就具体多了
api_http_requests_total{method="POST", handler="/messages"}
```

## Metric类型

Counter：只会往上增的数据类型, 比如API请求次数

Gauge: 可增可减，比如车速显示仪器

Histogram: 

​        对每个采样点进行统计，打到各个分类值中(bucket)
​        对每个采样点值累计和(sum)
​        对采样点的次数累计和(count)

​       需要3个metric

​       [basename]_bucket{le="上边界"}， [basename]_sum， [basename]_count

​       eg: 比如统计API请求时间, 次数，累加的总时间

```shell
# histogram 数据例子
histogram: <
  sample_count: 1000
  sample_sum: 29969.50000000001
  bucket: <
    cumulative_count: 192
    upper_bound: 20
  >
  bucket: <
    cumulative_count: 366
    upper_bound: 25
  >
  bucket: <
    cumulative_count: 501
    upper_bound: 30
  >
  bucket: <
    cumulative_count: 638
    upper_bound: 35
  >
  bucket: <
    cumulative_count: 816
    upper_bound: 40
  >
>
```



Summary： 

​      和Histogram比较类似

​      正态分布一样，统计低于60分不及格的同学比例，统计低于80分的同学比例，统计低于95分的同学比例

​      统计班上所有同学的总成绩(sum)   统计班上同学的考试总人数(count)

##  Prometheus查询

四种表达式:

- 瞬间向量  同一时刻 抓取的所有的  标量的数据
- 范围向量
- scalar标量
- string

时间选择器

- http_requests_total   ->  选择所有时刻的标量
- http_requests_total{job="prometheus",group="canary"}

范围选择

> 时间长度被追加在向量选择器尾部的方括号[]中
>
> 时间单位： ms s n h d w y
>
> eg: http_requests_total{job="prometheus"}[5m]

偏移

> http_requests_total offset 5m   -> 最近5分钟
>
> sum(http_requests_total{method="GET"} offset 5m)
>
> rate(http_requests_total[5m] offset 1w)





