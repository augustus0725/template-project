'use client';

import {useEffect} from "react";

const Error = ({error, reset}) => {
    useEffect(() => {
        console.error(error);
    }, [error],
        () => {
        console.log("leaving...");
    })

    return (
        <>
            <p> It is error page. </p>
        </>
    )
}

export default Error