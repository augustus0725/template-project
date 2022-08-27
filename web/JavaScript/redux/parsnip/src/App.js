
import './App.css';
import TasksPage from './components/TasksPage';
import { connect } from 'react-redux';
import { Component } from 'react';


class App extends Component {
  render() {
    return (
      <div className="main-content">
        <TasksPage tasks={this.props.tasks} />
      </div>
    );
  }
}

// state 是store的快照
function mapStateToProps(state) {
  return {
    tasks: state.tasks
  }
}

// 使用 connect 方法将需要关联 store的组件关联
export default connect(mapStateToProps)(App);
