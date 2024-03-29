import React, { Component } from "react";
import TaskList from './TaskList'

const TASK_STATUSES = ['Unstarted', 'In Progress', 'Completed'];

// 必须处理本地状态的时候, 使用继承Component的组件
class TasksPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showNewCardForm: false,
            title: '',
            description: '',
        };
    }

    onTitleChange = (e) => {
        this.setState({
            title: e.target.value
        });
    }

    onDescriptionChange = (e) => {
        this.setState({
            description: e.target.value
        });
    }

    resetForm() {
        this.setState({
            showNewCardForm: false,
            title: '',
            description: '',

        });
    }

    onCreateTask = (e) => {
        e.preventDefault();
        this.props.onCreateTask({ // 这个方法从外部传进来
            title: this.state.title,
            description: this.state.description,
        });
        this.resetForm();
    }

    toggleForm = () => {
        this.setState({
            showNewCardForm: !this.state.showNewCardForm
        });
    }


    renderTaskLists() {
        const { tasks } = this.props;
        return TASK_STATUSES.map(status => {
            const statusTasks = tasks.filter(task => task.status === status);
            return <TaskList key={status} status={status} tasks={statusTasks} />
        });
    };

    render() {
        return (
            <div className="task-list">
                <div className="task-list-header">
                    <button className="button button-default"
                        onClick={this.toggleForm}
                    >
                        + New task
                    </button>
                </div>
                {this.state.showNewCardForm && (
                    <form className="task-list-form" onSubmit={this.onCreateTask}>
                        <input className="full-width-input"
                            onChange={this.onTitleChange}
                            value={this.state.title}
                            type="text"
                            placeholder="title"
                        />

                        <input className="full-width-input"
                            onChange={this.onDescriptionChange}
                            value={this.state.description}
                            type="text"
                            placeholder="description"
                        />

                        <button
                            className="button" type="submit">
                            Save
                        </button>
                    </form>
                )}


                <div className="tasks">
                    <div className="task-lists">
                        {this.renderTaskLists()}
                    </div>
                </div>
            </div>
        )


    };
}

export default TasksPage;

