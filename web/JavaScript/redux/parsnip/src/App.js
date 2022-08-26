import logo from './logo.svg';
import './App.css';
import TasksPage from './components/TasksPage';


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

function App() {
  return (
    <div className="main-content">
      <TasksPage tasks={mockTasks} />
    </div>
  );
}

export default App;
