requirejs解决复杂js之间的依赖, 实现模块

# 代码构建的过程可以用requirejs来组织模板, 代码完成之后是不是可以优化成单个文件
# 并对结果文件压缩

# js 模块化的两种规范
# CommonJS (nodejs里的规范，可用于web端)

// 加载完才往下执行
var math = require('math');
math.add(2, 3);


# AMD (requirejs实现了)

// 异步加载依赖的模块
require(['math'], function (math) {

　　math.add(2, 3);

});
