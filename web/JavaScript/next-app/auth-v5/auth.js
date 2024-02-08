import NextAuth from "next-auth"
import GitHub from "next-auth/providers/github"
import CredentialsProvider from "next-auth/providers/credentials"
import PrismaContext from "@/lib/db";
import {PrismaAdapter} from "@auth/prisma-adapter";

export const config = {
    adapter: PrismaAdapter(PrismaContext.db),
    providers: [
        // 第三方
        GitHub,
        // 表单方式
        CredentialsProvider({
            name: "用户密码登录",
            credentials: {
                username: {label: "Username"},
                password: {label: "Password", type: "password"}
            },
            async authorize(credentials) {
                console.log("credentials: ", credentials)
                const authResponse = await fetch("/users/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(credentials),
                })

                if (!authResponse.ok) {
                    return null
                }

                const user = await authResponse.json()

                return user
            },
        }),
    ],
    callbacks: {
        authorized({request, auth}) {
            const {pathname} = request.nextUrl;
            console.log("authorized: ", pathname);
            return true;
        }
    },
    session: {
        strategy: "jwt",
    },
}


export const {
    handlers: {GET, POST}, auth, signIn, signOut,
} = NextAuth(config)