// require.config({
//
//     baseUrl: "js/lib",
//
//     paths: {
//
//         "jquery": "jquery.min",
//         "underscore": "underscore.min",
//         "backbone": "backbone.min"
//
//     }
//
// });


requirejs(["helper/util"], function(util) {
    // 当依赖的js下载完毕之后运行下面的程序
    //This function is called when scripts/helper/util.js is loaded.
    //If util.js calls define(), then this function is not fired until
    //util's dependencies have loaded, and the util argument will hold
    //the module value for "helper/util".
    alert(util.add(100, 200));
    alert(util.mul(12, 13));
});