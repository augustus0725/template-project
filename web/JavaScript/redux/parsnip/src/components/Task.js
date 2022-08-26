import React from "react";

// jsx: js的语法 with html template.
// 无状态组件
// 没有生命周期方法
// 不能使用 this.state 和 this.setState
const Task =  props => {
    return (
        <div className="task">
            <div className="task-header">
                <div>{props.task.title}</div>
            </div>
            <hr/>
            <div className="task-body">{props.task.description}</div>
        </div>

    );
}

export default Task;
