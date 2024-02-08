import PrismaContext from "@/lib/db";

import {NextResponse} from "next/server";
import {console} from "next/dist/compiled/@edge-runtime/primitives";

export async function POST(req) {
    console.log("create a user now ..........................")
    // const user = await prisma.user.create({data: {name: "Kyle"}})
    const users = await PrismaContext.db.user.findMany();
    console.log("user is :", users);
    return NextResponse.json({status: "success"});
}