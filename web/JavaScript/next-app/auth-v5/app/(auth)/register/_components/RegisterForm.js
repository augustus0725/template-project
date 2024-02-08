"use client";

import { useForm } from "react-hook-form"
import { zodResolver } from '@hookform/resolvers/zod';
import bcrypt from "bcryptjs"
import * as z from "zod"

const schema = z.object({
    email: z.string().email({message: "Should be email!"}).min(5, { message: 'Required' }),
    password: z.string().min(6, {message: "At least 6 characters."}).default("******"),
});

const RegisterForm = () => {
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

    const onSubmit = async (data) => {
        console.log(data);
        console.log(errors);

        const hashPassword = await bcrypt.hash(data.password, 10);
        const authResponse = await fetch("/api/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                ...data,
                password: hashPassword,
            }),
        })
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

            <button type={"submit"}> 注册 </button>
        </form>
    )
}

export default RegisterForm