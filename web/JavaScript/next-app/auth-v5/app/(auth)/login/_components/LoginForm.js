"use client";

import {useForm} from "react-hook-form"
import {zodResolver} from '@hookform/resolvers/zod';
import {LoginSchema} from "@/schemas";
import {login} from "@/actions/login";

const LoginForm = () => {
    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm(
        {
            resolver: zodResolver(LoginSchema),
        }
    )

    const onSubmit = async (data) => {
        const response = await login(data);
        console.log(response);
    }

    // console.log(watch("email")) // watch input value by passing the name of it

    return (
        /* "handleSubmit" will validate your inputs before invoking "onSubmit" */
        <form onSubmit={handleSubmit(onSubmit)}>
            {/* register your input into the hook by invoking the "register" function */}
            <input placeholder={"abc@d.com"} {...register("email")} />
            {errors.email?.message && <span>{errors.email?.message}</span>}

            {/* include validation with required or other standard HTML validation rules */}
            <input type={"password"} placeholder={"******"} {...register("password", { required: true })} />
            {/* errors will return when field validation fails  */}
            {errors.password?.message && <span>{errors.password?.message}</span>}<br/>

            <button type={"submit"}> 登录 </button>
        </form>
    )
}

export default LoginForm