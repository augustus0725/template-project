const _ = require('lodash')

let a = {b:1, c: {d:100}}

console.log(_.toPairs(a))
console.log('keys is:',  _.keys(a))

console.log('[1, 2, 3] xor [2, 3, 4] === ', _.xor([1, 2, 3], [2, 3, 4]))
console.log('[1, 2, 3] xor [2, 3, 4] xor [4, 5] === ', _.xor([1, 2, 3], [2, 3, 4]))
console.log('_.intersection([2, 1], [4, 2], [1, 2]) === ', _.intersection(...[[2, 1], [4, 2], [1, 2]]))
console.log('_.intersection([2, 1]) === ', _.intersection([2, 1]))

var users = [
    { 'user': 'barney',  'age': 36, 'active': true },
    { 'user': 'fred',    'age': 40, 'active': false },
    { 'user': 'pebbles', 'age': 1,  'active': true }
];

console.log('Find age-> 36 from users :', _.find(users, {age: 36}))
console.log('[1, 2, 3] 3 in it: ', _.some([1, 2, 3], v => v===3))
console.log('[1, 2, 3] 4 in it: ', _.some([1, 2, 3], v => v===4))

console.log('{a:1, b:2} omit a is : ', _.omit({a:1, b:2}, ['a']))

console.log('{a: 1, b: 2} has a -> ', _.has({a:1, b:2}, ['a']))

console.log(_.create({ 'a': _.create({ 'b': 2 }) }))