"use client";

import { useForm } from "react-hook-form"
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from "zod"

const schema = z.object({
    email: z.string().email({message: "Should be email!"}).min(5, { message: 'Required' }),
    password: z.string().min(6, {message: "At least 6 characters."}).default("******"),
});

const LoginForm = () => {
    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm(
        {
            resolver: zodResolver(schema),
        }
    )

    const onSubmit = (data) => {
        console.log(data);
        console.log(errors);
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

            <input type="submit" />
        </form>
    )
}

export default LoginForm