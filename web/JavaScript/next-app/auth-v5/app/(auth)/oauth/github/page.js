"use client"

import {signIn} from "next-auth/react";

const GithubLogin = () => {
    return (
        <div>
            <button onClick={(e) => {
                signIn("github", {
                    callbackUrl: "http://localhost:3001/oauth/github/",
                })
            }}> sign in
            </button>
        </div>
    )
}

export default GithubLogin;