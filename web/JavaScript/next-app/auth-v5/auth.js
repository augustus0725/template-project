import NextAuth from "next-auth"
import PrismaContext from "@/lib/db";
import {PrismaAdapter} from "@auth/prisma-adapter";
import authConfig from "@/auth.config";

export const {
    handlers: {GET, POST}, auth, signIn, signOut,
} = NextAuth({
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