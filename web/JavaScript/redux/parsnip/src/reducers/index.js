import { uniqueId } from "../actions";

const mockTasks = [
  {
    id: uniqueId(),
    title: 'Learn Redux',
    description: 'One.......................',
    status: 'In Progress',
  },
  {
    id: uniqueId(),
    title: 'Learn Redux two',
    description: 'Two.......................',
    status: 'In Progress',
  },

];

//  reducer负责初始化状态, 这个是同步action的解决方案
export default function tasks(state = { tasks: mockTasks }, action) {
  if (action.type === 'CREATE_TASK') {
    return { tasks: state.tasks.concat(action.payload) };
  }
  if (action.type === 'FETCH_TASKS_SUCCEEDED') {
    return { tasks: [...state.tasks, ...action.payload.tasks]};
  }
  return state;
}