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
        async linkAccount({user}) {
            console.log({current: user});
            await PrismaContext.db.user.update({
                where: {id: user.id},
                data: {emailVerified: new Date()}
            })
        }
    },
    callbacks: {
        async signIn({user, account}) {
            // Allow OAuth without email verification
            if (account?.provider !== "credentials") return true;
            // TODO 邮件认证
            // TODO 多因子

            return true;

        },
        async jwt({token, account}) {
            if (account) {
                token.accessToken = account.access_token
                token.refreshToken = account.refresh_token
            }
            return token;
        },
        async session({token, session}) {
            if (token.accessToken) {
                session.accessToken = token.accessToken;
            }
            if (token.refreshToken) {
                session.refreshToken = token.refreshToken;
            }
            return session;
        },
    },
    // 可以把通过oauth2/google/...认证的信息放到数据库里
    // adapter: PrismaAdapter(PrismaContext.db),
    session: {strategy: "jwt"},
    ...authConfig
})