import { history, request } from 'umi';

export const render = async (oldRender) => {
    // 检验权限
    if (false) {
        history.push('/login');
    }
    // 继续渲染
    oldRender();    
}