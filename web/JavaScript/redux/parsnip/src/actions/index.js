import * as api from '../api'

let _id = 1;

export function uniqueId() {
    return _id++;
}

export function createTask({title, description}) {
    return {
        type: 'CREATE_TASK',
        payload: {
            id: uniqueId(),
            title,
            description,
            status: 'Unstarted',
        }
    };
}

export function fetchTasksSucceeded(tasks) {
    return {
      type: 'FETCH_TASKS_SUCCEEDED',
      payload: {
        tasks
      }
    }
  }
  
  // 异步action的解决方案， 注意产生的是函数
  export function fetchTasks() {
    return dispatch => {
      api.fetchTasks()
      .then(resp => {
        dispatch(fetchTasksSucceeded(resp.data));
      });    
    }
  }