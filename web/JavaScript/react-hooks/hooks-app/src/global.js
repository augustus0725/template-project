import {makeAutoObservable} from "mobx";

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

export const GlobalTimer = createTimer()