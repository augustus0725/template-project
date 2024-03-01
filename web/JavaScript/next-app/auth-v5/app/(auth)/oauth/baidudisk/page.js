"use client"

import {signIn} from "next-auth/react";

const BaiduDiskLogin = () => {
    return (
        <div>
            <button onClick={(e) => {
                signIn("baidudisk-provider", {
                    callbackUrl: "http://localhost:3001/oauth/custom/",
                })
            }}> sign in
            </button>
        </div>
    )
}

export default BaiduDiskLogin;