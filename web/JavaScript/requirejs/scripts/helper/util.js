// AMD模块的写法

define(function () {
    let add = function (x,y) {
        return x + y;
    };

    let mul = function (x,y) {
        return x * y;
    };

    return {
        add: add,
        mul: mul,
    };
})