import NextAuth from "next-auth"
import PrismaContext from "@/lib/db";
import {PrismaAdapter} from "@auth/prisma-adapter";
import authConfig from "@/auth.config";

export const {
    handlers: {GET, POST}, auth, signIn, signOut,
} = NextAuth({
    // 转到自己定义的页面
    pages: {
        signIn: "/login",
        error: "/error",
    },
    events: {
        // github 登录会更新emailVerified的时间
        async linkAccount({ user }) {
            console.log({current: user});
            await PrismaContext.db.user.update({
                where: { id: user.id },
                data: { emailVerified: new Date() }
            })
        }
    },
    callbacks: {
        async jwt({token}) {
            console.log({token});
            return token;
        },
        async session({token, session}) {
            console.log({session: session});
            console.log({jwt: token});
            return session;
        },
    },
    adapter: PrismaAdapter(PrismaContext.db),
    session: {strategy: "jwt"},
    ...authConfig
})