import Github from "next-auth/providers/github";
import CredentialsProvider from "next-auth/providers/credentials";
import {LoginSchema} from "@/schemas";
import PrismaContext from "@/lib/db";
import bcrypt from "bcryptjs";

const authConfig = {
    providers: [
        Github({
            clientId: process.env.AUTH_GITHUB_ID,
            clientSecret: process.env.AUTH_GITHUB_SECRET,
        }),
        // 表单方式
        CredentialsProvider({
            // name: "用户密码登录",
            // credentials: {
            //     username: {label: "Username"},
            //     password: {label: "Password", type: "password"}
            // },
            async authorize(credentials) {
                const validatedFields = LoginSchema.safeParse(credentials);
                if (validatedFields.success) {
                    const { email, password } = validatedFields.data;
                    // 直接调用服务端的方式
                    try {
                        const user = await PrismaContext.db.user.findUnique({ where: { email } });
                        if (!user || !user.password) return null;

                        const passwordsMatch = await bcrypt.compare(
                            password,
                            user.password,
                        );

                        console.log(`passwordsMatch is ${passwordsMatch}`);

                        if (passwordsMatch) return user;
                    } catch {
                        return null;
                    }
                }
                return null;

                // 采用 api的方式 调用

                // const authResponse = await fetch("/users/login", {
                //     method: "POST",
                //     headers: {
                //         "Content-Type": "application/json",
                //     },
                //     body: JSON.stringify(credentials),
                // })

                // if (!authResponse.ok) {
                //     return null
                // }

                // const user = await authResponse.json()
                //
                // return user

            },
        }),
    ],
}

export default authConfig;