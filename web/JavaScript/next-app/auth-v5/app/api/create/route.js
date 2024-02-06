import {PrismaClient} from "@prisma/client";
import {NextResponse} from "next/server";
import {console} from "next/dist/compiled/@edge-runtime/primitives";

export async function POST(req) {
    const prisma = new PrismaClient();

    console.log("create a user now ..........................")
    // const user = await prisma.user.create({data: {name: "Kyle"}})
    const users = await prisma.user.findMany();
    console.log("user is :", users);

    await prisma.$disconnect();
    return NextResponse.json({status: "success"});
}