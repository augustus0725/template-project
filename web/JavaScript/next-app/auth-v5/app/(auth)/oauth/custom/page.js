"use client"

import {signIn} from "next-auth/react";

const CustomLogin = () => {
    return (
        <div>
            <button onClick={(e) => {
                signIn("my-oidc-provider", {
                    callbackUrl: "http://localhost:3001/oauth/custom/",
                })
            }}> sign in
            </button>
        </div>
    )
}

export default CustomLogin;