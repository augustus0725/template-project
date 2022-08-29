import { fork } from 'redux-saga/effects';

export default function* rootSaga() {
    console.log('This is root sage.');
    // fork 能让程序不阻塞
    yield fork(watchFetchTasks);
    yield fork(watchFetchTasks1);
}

function* watchFetchTasks() {
    console.log('Watch fetch tasks......');
}

function* watchFetchTasks1() {
    console.log('1 Watch fetch tasks......');
}