import { memo } from "react";

const Child = ({count, callback}) => {
    const show = () => {
        console.log("callBack()");

    };

    return (
        <>
            {show()}
            {callback()}
            <div> This is child, count is : {count} </div>
        </>
    );
};

// 用memo包装组件之后, 子组件的状态变化只会在依赖的状态（比如: count）变化之后才会重新计算
export default memo(Child);