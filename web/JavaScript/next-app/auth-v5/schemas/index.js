import * as z from "zod";

export const LoginSchema = z.object({
    email: z.string().email({message: "Should be email!"}).min(5, { message: 'Required' }),
    password: z.string().min(6, {message: "At least 6 characters."}).default("******"),
});