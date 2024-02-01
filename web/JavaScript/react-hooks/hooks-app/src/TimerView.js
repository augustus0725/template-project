import {observer} from "mobx-react-lite";


// Build a "user interface" that uses the observable state.
export default observer(({timer}) => (
    <button onClick={() => timer.increase()}>Seconds passed: {timer.secondsPassed}</button>
))
