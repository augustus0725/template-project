import { delay } from 'roadhog-api-doc'; // 模拟延迟

export default delay(
  {
    '/api/goods': [
      {id:1, name: 'peach'},
      {id:2, name: 'apple'},

    ],

    'POST /umi/login': (req, res) => {
      const {username, password} = req.body;

      if (username === "sabo" && password === "123") {
        res.send({
          err: 0,
          msg: '成功',
          role: 'user',
        });
      } else if (username === "admin" && password === "123") {
        res.send({
          err: 0,
          msg: '成功',
          role: 'admin',
        });
      }  else {
        res.send({
          err: 1,
          msg: '失败',
          role: 'anonymous',
        });
      }
    }
  },
  1000, // ms
);
