const mockTasks = [
    {
      id: 1,
      title: 'Learn Redux',
      description: 'One.......................',
      status: 'In Progress',
    },
    {
      id: 2,
      title: 'Learn Redux two',
      description: 'Two.......................',
      status: 'In Progress',
    },
  
  ];

//  reducer负责初始化状态
export default function tasks(state = {tasks: mockTasks}, action) {
    return state;
}