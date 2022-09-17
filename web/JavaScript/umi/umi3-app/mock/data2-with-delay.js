import { delay } from 'roadhog-api-doc'; // 模拟延迟

export default delay(
  {
    '/api/goods': [
      {id:1, name: 'peach'},
      {id:2, name: 'apple'},

    ],
  },
  2000, // ms
);
