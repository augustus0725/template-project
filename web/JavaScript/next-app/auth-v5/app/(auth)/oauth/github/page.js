import {auth, signIn} from "@/auth";

const GithubLogin = async ({provider}) => {
    const session = await auth()

    console.log("session: ", session);
    return (
        <div>
            <form action={async () => {
                "use server"
                await signIn(provider)
            }}>
                <button type={"submit"}> sign in</button>
            </form>
        </div>
    )
}

export default GithubLogin;