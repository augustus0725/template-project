import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import tasks from './reducers';
import {applyMiddleware, legacy_createStore as createStore } from 'redux'
import { Provider } from 'react-redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import thunk from 'redux-thunk';
import createSagaMiddleware from '@redux-saga/core';
import rootSaga from './sagas';


// saga
const sagaMiddleware = createSagaMiddleware();

// const store = createStore(tasks);
// const store = createStore(tasks, devToolsEnhancer());
const store = createStore(tasks, composeWithDevTools(applyMiddleware(thunk, sagaMiddleware)));

// 运行root saga
sagaMiddleware.run(rootSaga);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
// react-redux 用Provider包含App, 让App组件的任何组件都能获取redux store, 从而将state 和 store关联
  <Provider store={store}>
    <App />
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();