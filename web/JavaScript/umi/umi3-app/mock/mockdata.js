import Mock from 'mockjs';
import { delay } from 'roadhog-api-doc'; // 模拟延迟


export default delay({
  '/mock/list': (req, res) => {
    res.send(Mock.mock(
      {
        // 随机返回数据的一个数据
        "data|1": [
          {
            code: 0,
            data: {
              id: 1, name: 'apple'
            }
          },
          {
            code: 1,
            data: {

            }
          },
        ]
      }

    ));
  },

  // 随机产生不同对象
  '/fake/list': (req, res) => {
    res.send(Mock.mock({
      ret: 0,
      data: {
        "mtime": "@datetime", // 随机产生时间
        "score|1-800": 800,//随机生成1-800的数字
        "nickname": "@cname",//随机生成中文名字
        'bad|1': Boolean, // 随机boolean
        'loved_nums|1-3': [3,5,4,6,23,28,42,45], // 随机产生数组，可以重复1-3次
        're-data': /[a-z][A-Z][0-9]/, // 用正则随机产生数据
      }
    }));
  },
},
1000);
//  另外如果要求高可以考虑用 jsonserver 来做mock
