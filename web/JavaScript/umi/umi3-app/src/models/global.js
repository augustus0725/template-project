import { request } from 'umi';

export default {
    namespace: 'global', // 这个现在可以省略了，文件名字作为namespace
    // 全局数据
    state: {
        title: 'global-title',
        login: false,
    },

    // 同步的事件
    reducers: {
        setTitle(state) {
            return {
                ...state,
                title: 'Title now: ' + Math.random().toFixed(2),
            };
        },

        signin(state, action) {
            let login = action.payload.err === 0;
            return {
                ...state,
                login: login,
            }

        },
    },

    // 异步的请求
    effects: {
        *login(action, { call, put, select }) {
            const data = yield call(request, '/umi/login', {
                method: 'post',
                // headers 自动加了 application/json
                data: {
                    username: action.payload.username,
                    password: action.payload.password,
                },
            });

            yield put({
                type: 'signin',
                payload: data,
            });
        }
    }


}

// ！！！  单个页面级别的数据可以放在 @/pages/<page 1>/model.js
// ！！！  页面级别 多个modle 数据可以放在 @/pages/<page 1>/models