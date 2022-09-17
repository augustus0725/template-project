export default {
  'GET /umi/goods': [
    {id: 1, name: '西瓜'},
    {id: 2, name: '西红柿'},
  ],
  // 函数的方式
  'POST /api/users/create': (req, res) => {
    res.send("ok");
  },
}
