import NextAuth from "next-auth"
import GitHub from "next-auth/providers/github"

export const config = {
    providers: [GitHub,],
    callbacks: {
      authorized({request, auth}) {
          const { pathname } = request.nextUrl;
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