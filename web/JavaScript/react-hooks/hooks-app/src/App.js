import './App.css';
import {useState, useEffect, useContext, useReducer, useMemo, useCallback, useRef} from 'react';
import {ThemeContext} from './context';
import Child from './Child';
import {makeAutoObservable} from "mobx";
import TimerView from "./TimerView";
import {GlobalTimer} from "./global";

const initialState = {count: 0};

function reducer(state, action) {
    switch (action.type) {
        case 'increment':
            return {count: state.count + 1};
        case 'decrement':
            return {count: state.count - 1};
        default:
            throw new Error();
    }
}

// mobx
function createTimer() {
    return makeAutoObservable({
        secondsPassed: 0,
        increase() {
            this.secondsPassed += 1;
        },
        reset() {
            this.secondsPassed = 0;
        }
    })
}


function App() {
    const [count, setCount] = useState(0);
    // 新的内部状态 num
    const [num, setNum] = useState(1000);
    // useRef 来判断渲染的次数, 不会引起渲染
    const refCount = useRef(0);
    // 使用useRef绑定表单对象
    const inputRef = useRef();

    const theme = useContext(ThemeContext);
    // reducer (redux way)
    const [state, dispatch] = useReducer(reducer, initialState);

    function doubleSetCount() {
        // 这种方式会导致只调用一次 setCount
        setCount(count - 1)
        setCount(count - 1)
        // 应该这样调用
        setCount(preCount => preCount - 1)
        setCount(preCount => preCount - 1)
    }

    // -> 变化 count 的时候也会触发 getNum 计算, 如果getNum 是效率比较低的函数，那是不是就有效率问题?
    // 解决方式是使用hook -> useMemo  限定哪些状态变化触发自己重新计算
    const getNumWithMemo = useMemo(() => {
        console.log("getNumWithMemo....");
        return 100 + count;
    }, [num]);

    // 现版本react， 无论是否修改到影响子组件的状态元素, 子组件都会渲染
    // 应该限制一下, 比如说我只用到了 count， 那应该只有 count 变了， 才引起child渲染
    // 方法： 使用高阶组件 memo， 可以看下Child 组件里的注释 和 用法

    // 子组件依赖的 [变量] 可以用 memo来避免错误渲染
    //             memo 对依赖的函数 没有作用
    //          --> 解决方法是useCallback
    const callback = useCallback(() => {
            console.log("Invoke callback");
        },
        [num]); // 这里可以定义 和 哪些状态 有关， 之后 变化会引起 子模块的重新计算


    useEffect(() => {
        // 修改 DOM
        // 字符串里的变量采用ES6语法， 字符串模板
        // 完成对 DOM 的更改后运行你的“副作用”函数
        document.title = `You have clicked ${count} times`;
        refCount.current = refCount.current + 1;
        console.log("渲染的次数是: " + refCount.current)
    }, [count]);

    console.log('app render....')

    return (
        <div className="App">
            <input ref={inputRef}/>
            <div> Learn react hooks</div>
            {/*<div style={{background: theme.background, color: theme.foreground}}> {count} </div>*/}

            <div> ---------------------------</div>
            <div> count is {count} </div>
            <div> num is {num} </div>
            <div> num with memo is {getNumWithMemo} </div>
            <div> ---------------------------</div>
            <Child callback={callback} num={num}></Child>
            <div> ---------------------------</div>
            <div> ------ count: {state.count} ------</div>
            <div style={{background: theme.background, color: theme.foreground}}> redux way count: {state.count} </div>
            <div> ---------------------------</div>

            <TimerView timer={GlobalTimer}/>

            <button onClick={() => setCount(prevState => prevState + 1)}> Click me!</button>
            <button onClick={() => dispatch({type: 'increment'})}> Click me with reducer!</button>
            <button onClick={() => setNum(prevState => prevState + 1)}> Change Num</button>
            <button onClick={() => console.log(inputRef.current.value)}> show ref data </button>
        </div>
    );
}

export default App;
