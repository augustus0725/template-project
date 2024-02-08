import NextAuth from "next-auth"
import PrismaContext from "@/lib/db";
import {PrismaAdapter} from "@auth/prisma-adapter";
import authConfig from "@/auth.config";

export const {
    handlers: {GET, POST}, auth, signIn, signOut,
} = NextAuth({
    adapter: PrismaAdapter(PrismaContext.db),
    session: {strategy: "jwt"},
    ...authConfig
})