export default {
    namespace: 'global', // 这个现在可以省略了，文件名字作为namespace
    // 全局数据
    state: {
        title: 'global-title',
        login: false,
    },

    // reducer
    reducers: {
        setTitle(state) {
            return {
                ...state,
                title: 'Title now: ' + Math.random().toFixed(2),
            };
        }, 
    }

}